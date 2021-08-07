package pl.coderslab.user;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.utils.DBUtil;
import pl.coderslab.utils.DbUtil;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class userDAO {

    private static final String CREATE = "insert into users(email,username, password) VALUES (?, ?, ?);";
    private static final String GET_ID = "select id from users where email = ?;";
    private static final String SHOW_USER = "select * from users where id = ?;";
    private static final String DELETE = "delete from users where id=?;";
    private static final String SHOW_ALL = "select * from users";
    private static final String HOW_MANY = "select count(*) from users;";
    private static final String UPDATE = "update users set email=?, username=?, password=? where id=?";


    public static void main(String[] args) {

    }

    public static User create(User user) {

        try (Connection conn = DbUtil.getConnection()) {

            PreparedStatement preStat = conn.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);

            preStat.setString(1, user.getEmail());
            preStat.setString(2, user.getUsername());
            preStat.setString(3, hashPassword(user.getPassword()));

            preStat.executeUpdate();
            ResultSet rs = preStat.getGeneratedKeys();

            System.out.println();

            if (rs.next()) {
                int id = rs.getInt(1);
                //System.out.println("Inserted ID: " + id);
                user.setId(id);
            }

            return user;

        }catch (SQLException e){
            e.printStackTrace();
            return  null;

        }

    }

    public static User read(int user_Id) {

        int user_id = user_Id;

        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement pretStat2 = conn.prepareStatement(SHOW_USER);
            pretStat2.setInt(1, user_id);
            ResultSet rs = pretStat2.executeQuery();
            String email = null;
            String username = null;
            String password = null;
            int Id = 0;
            if (rs.next()) {
                User user = new User(email, username, password);
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                //System.out.println(user.toString());
                return user;
            }

        } catch (SQLException  throwables) {
            throwables.printStackTrace();

        }
        return null;
    }

    public static void delete(int ID_to_delete) {

        /* *//*Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj ID");*//*
        int ID_to_delete = scanner.nextInt();*/

        try (Connection conn = DBUtil.connect()) {
            PreparedStatement pretStat3 = conn.prepareStatement(DELETE);
            pretStat3.setInt(1, ID_to_delete);
            pretStat3.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    public static User[] findAll (){
        User [] users = new User[0];

        try (Connection conn = DbUtil.getConnection()) {

            PreparedStatement preStat6 = conn.prepareStatement(HOW_MANY);
            ResultSet rs6 = preStat6.executeQuery();
            int quantity= 0;
            while (rs6.next()) {
                quantity = rs6.getInt("count(*)");
                System.out.println(quantity);
            }

            PreparedStatement preStat7 = conn.prepareStatement(SHOW_ALL);
            ResultSet rs7 = preStat7.executeQuery();

            for (int i = 0; i < quantity; i++) {

                int id =0;
                String email = null;
                String username = null;
                String password = null;

                while (rs7.next()){
                    User user = new User(email,username,password);
                    id = rs7.getInt("id"); user.setId(id);
                    email = rs7.getString("email"); user.setEmail(email);
                    username = rs7.getString("username"); user.setUsername(username);
                    password = rs7.getString("password"); user.setPassword(password);
                    User [] tempUsers = Arrays.copyOf(users, users.length+1);
                    tempUsers[tempUsers.length-1]=user;
                    users=tempUsers;
                }
                System.out.println(users[i]);
            }
            //System.out.println(Arrays.toString(users));

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        } return users;

    }

    public static String hashPassword (String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());

    }

    public static void update(User user) {

        try (Connection conn = DBUtil.connect()) {
            PreparedStatement stmt3 = conn.prepareStatement(UPDATE);
            stmt3.setString(2, user.getUsername());

            stmt3.setString(1, user.getEmail());

            stmt3.setString(3, BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));

            stmt3.setInt(4, user.getId());
            stmt3.executeUpdate();
            System.out.println(user);
        } catch (SQLException e) {
            e.printStackTrace();

        }


    }
    public static void update2(User user, String username, String email, String password) {

        try (Connection conn = DbUtil.getConnection()) {

            PreparedStatement preStat10 = conn.prepareStatement(UPDATE);

            preStat10.setString(1, email);
            user.setEmail(email);
            preStat10.setString(2, username);
            user.setUsername(username);
            preStat10.setString(3, password);
            user.setPassword(password);
            preStat10.setInt(4, user.getId());

            preStat10.executeUpdate();
            read(user.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
