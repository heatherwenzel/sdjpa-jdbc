package com.heatherwenzel.springdatademo.dao;

import com.heatherwenzel.springdatademo.domain.Book;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class BookDaoImpl implements BookDao {

    private final DataSource source;

    public BookDaoImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public Book getById(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM book where id = ?");
            ps.setLong(1, id);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getBookFromRS(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(resultSet, ps, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public Book findByTitle(String title) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM book where title = ?");
            ps.setString(1, title);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getBookFromRS(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(resultSet, ps, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    @Override
    public Book saveBook(Book book) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("INSERT INTO book (isbn, publisher, title, author_id) values (?, ?, ?, ?)");
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getPublisher());
            ps.setString(3, book.getTitle());
            ps.setLong(4, book.getAuthorId());
            ps.execute();

            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");

            if (resultSet.next()) {
                Long savedId = resultSet.getLong(1);
                return this.getById(savedId);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(resultSet, ps, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public Book updateBook(Book book) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("UPDATE book set isbn = ?, publisher = ?, title = ?, author_id = ? where id = ?");
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getPublisher());
            ps.setString(3, book.getTitle());
            ps.setLong(4, book.getAuthorId());
            ps.setLong(5, book.getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(null, ps, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return this.getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("DELETE from book where id = ?");

            ps.setLong(1, id);
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                closeAll(null, ps, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Book getBookFromRS(ResultSet resultSet) throws SQLException {
        Book book = new Book();

        book.setId(resultSet.getLong(1));
        book.setIsbn(resultSet.getString(2));
        book.setPublisher(resultSet.getString(3));
        book.setTitle(resultSet.getString(4));
        book.setAuthorId(resultSet.getLong(5));

        return book;
    }

    private void closeAll(ResultSet resultSet, PreparedStatement ps, Connection connection) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }

        if (ps != null){
            ps.close();
        }

        if (connection != null){
            connection.close();
        }
    }
}
