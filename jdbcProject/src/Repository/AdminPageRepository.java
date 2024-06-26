package Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import static Repository.InitDataRepository.*;

public class AdminPageRepository {
	
	private static Statement stmt;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	private static String dropMsSql = "drop table if exists db1.movie_schedule;";
	private static String dropTheaterSql = "drop table if exists db1.Theater;";
	private static String dropMovieSql = "drop table if exists db1.movie;";
	private static String dropSeatSql = "drop table if exists db1.seat;";
	private static String dropTicketSql = "drop table if exists db1.Ticket;";
	private static String dropUserSql = "drop table if exists db1.User;";
	private static String dropReservationSql = "drop table if exists db1.reservation;";
	private static String dropMssSql = "drop table if exists db1.movie_schedule_seat;";
	private static String createMovieSql = "CREATE TABLE IF NOT EXISTS `db1`.`movie` ("
			+ "  `m_id` INT NOT NULL AUTO_INCREMENT,"
			+ "  `m_name` VARCHAR(20) NOT NULL,"
			+ "  `m_director` VARCHAR(10) NULL,"
			+ "  `m_genre` VARCHAR(50) NULL,"
			+ "  `m_running_time` INT NULL,"
			+ "  `m_film_rating` VARCHAR(10) NULL,"
			+ "  `m_release_date` DATE NOT NULL,"
			+ "  `m_actor` VARCHAR(50) NULL,"
			+ "  `m_info` TEXT NULL,"
			+ "  `m_grade` DOUBLE NULL,"
			+ "  PRIMARY KEY (`m_id`))"
			+ "ENGINE = InnoDB;";
	private static String createTheaterSql =
			"CREATE TABLE IF NOT EXISTS `db1`.`Theater` ("
			+ "  `thtr_id` INT NOT NULL AUTO_INCREMENT,"
			+ "  `thtr_total_seat` INT NOT NULL,"
			+ "  `thtr_available` TINYINT(1) NOT NULL,"
			+ "  `thtr_x` INT NOT NULL,"
			+ "  `thtr_y` INT NOT NULL,"
			+ "  PRIMARY KEY (`thtr_id`))"
			+ "ENGINE = InnoDB;";
	private static String createMsSql =
			"CREATE TABLE IF NOT EXISTS `db1`.`movie_schedule` ("
			+ "  `ms_id` INT NOT NULL AUTO_INCREMENT,"
			+ "  `ms_date` DATE NOT NULL,"
			+ "  `ms_day_of_week` VARCHAR(10) NOT NULL,"
			+ "  `ms_shedule_cnt` INT NOT NULL,"
			+ "  `ms_time` TIME NOT NULL,"
			+ "  `movie_m_id` INT NOT NULL,"
			+ "  `Theater_thtr_id` INT NOT NULL,"
			+ "  PRIMARY KEY (`ms_id`),"
			+ "  INDEX `fk_movie_schedule_movie_idx` (`movie_m_id` ASC) VISIBLE,"
			+ "  INDEX `fk_movie_schedule_Theater1_idx` (`Theater_thtr_id` ASC) VISIBLE,"
			+ "  CONSTRAINT `fk_movie_schedule_movie`"
			+ "    FOREIGN KEY (`movie_m_id`)\r\n"
			+ "    REFERENCES `db1`.`movie` (`m_id`)"
			+ "    ON DELETE CASCADE "
			+ "    ON UPDATE CASCADE,"
			+ "  CONSTRAINT `fk_movie_schedule_Theater1`"
			+ "    FOREIGN KEY (`Theater_thtr_id`)"
			+ "    REFERENCES `db1`.`Theater` (`thtr_id`)"
			+ "    ON DELETE CASCADE "
			+ "    ON UPDATE CASCADE )"
			+ "ENGINE = InnoDB;";
	private static String createSeatSql =
			"CREATE TABLE IF NOT EXISTS `db1`.`seat` ("
			+ "  `seat_id` INT NOT NULL AUTO_INCREMENT,"
			+ "  `seat_number` INT NOT NULL,"
			+ "  `Theater_thtr_id` INT NOT NULL,"
			+ "  PRIMARY KEY (`seat_id`),"
			+ "  INDEX `fk_seat_Theater1_idx` (`Theater_thtr_id` ASC) VISIBLE,"
			+ "  CONSTRAINT `fk_seat_Theater1`"
			+ "    FOREIGN KEY (`Theater_thtr_id`)"
			+ "    REFERENCES `db1`.`Theater` (`thtr_id`)"
			+ "    ON DELETE CASCADE "
			+ "    ON UPDATE CASCADE)"
			+ "ENGINE = InnoDB;";
	private static String createTicketSql =
			"CREATE TABLE IF NOT EXISTS `db1`.`Ticket` ("
			+ "  `tckt_id` INT NOT NULL AUTO_INCREMENT,"
			+ "  `tckt_availability` TINYINT(1) NOT NULL,"
			+ "  `tckt_standard_price` INT NOT NULL,"
			+ "  `tckt_selling_price` INT NOT NULL,"
			+ "  `seat_seat_id` INT NOT NULL,"
			+ "  `movie_schedule_ms_id` INT NOT NULL,"
			+ "  PRIMARY KEY (`tckt_id`),"
			+ "  INDEX `fk_Ticket_seat1_idx` (`seat_seat_id` ASC) VISIBLE,"
			+ "  INDEX `fk_Ticket_movie_schedule1_idx` (`movie_schedule_ms_id` ASC) VISIBLE,"
			+ "  CONSTRAINT `fk_Ticket_seat1`"
			+ "    FOREIGN KEY (`seat_seat_id`)"
			+ "    REFERENCES `db1`.`seat` (`seat_id`)"
			+ "    ON DELETE CASCADE"
			+ "    ON UPDATE CASCADE,"
			+ "  CONSTRAINT `fk_Ticket_movie_schedule1`"
			+ "    FOREIGN KEY (`movie_schedule_ms_id`)"
			+ "    REFERENCES `db1`.`movie_schedule` (`ms_id`)"
			+ "    ON DELETE CASCADE"
			+ "    ON UPDATE CASCADE)"
			+ "ENGINE = InnoDB;";
	private static String createUserSql =
			"CREATE TABLE IF NOT EXISTS `db1`.`User` ("
			+ "  `user_id` INT NOT NULL AUTO_INCREMENT,"
			+ "  `user_name` VARCHAR(20) NOT NULL,"
			+ "  `user_phone_num` VARCHAR(15) NOT NULL,"
			+ "  `user_email` VARCHAR(45) NOT NULL,"
			+ "  `user_admin` TINYINT(1) NOT NULL,"
			+ "  PRIMARY KEY (`user_id`))"
			+ "ENGINE = InnoDB;";
	private static String createReservationSql =
			"CREATE TABLE IF NOT EXISTS `db1`.`reservation` (\r\n"
			+ "  `r_id` INT NOT NULL AUTO_INCREMENT,\r\n"
			+ "  `r_payment` VARCHAR(20) NOT NULL,\r\n"
			+ "  `r_pay_status` VARCHAR(10) NOT NULL,\r\n"
			+ "  `r_pay_amount` INT NOT NULL,\r\n"
			+ "  `r_pay_date` DATETIME NOT NULL,\r\n"
			+ "  `User_user_id` INT NOT NULL,\r\n"
			+ "  `movie_schedule_ms_id` INT NOT NULL,\r\n"
			+ "  `Ticket_tckt_id` INT NOT NULL,\r\n"
			+ "  PRIMARY KEY (`r_id`),\r\n"
			+ "  INDEX `fk_reservation_User1_idx` (`User_user_id` ASC) VISIBLE,\r\n"
			+ "  INDEX `fk_reservation_movie_schedule1_idx` (`movie_schedule_ms_id` ASC) VISIBLE,\r\n"
			+ "  INDEX `fk_reservation_Ticket1_idx` (`Ticket_tckt_id` ASC) VISIBLE,\r\n"
			+ "  CONSTRAINT `fk_reservation_User1`\r\n"
			+ "    FOREIGN KEY (`User_user_id`)\r\n"
			+ "    REFERENCES `db1`.`User` (`user_id`)\r\n"
			+ "    ON DELETE CASCADE\r\n"
			+ "    ON UPDATE CASCADE,\r\n"
			+ "  CONSTRAINT `fk_reservation_movie_schedule1`\r\n"
			+ "    FOREIGN KEY (`movie_schedule_ms_id`)\r\n"
			+ "    REFERENCES `db1`.`movie_schedule` (`ms_id`)\r\n"
			+ "    ON DELETE CASCADE\r\n"
			+ "    ON UPDATE CASCADE,\r\n"
			+ "  CONSTRAINT `fk_reservation_Ticket1`\r\n"
			+ "    FOREIGN KEY (`Ticket_tckt_id`)\r\n"
			+ "    REFERENCES `db1`.`Ticket` (`tckt_id`)\r\n"
			+ "    ON DELETE CASCADE\r\n"
			+ "    ON UPDATE CASCADE)\r\n"
			+ "ENGINE = InnoDB;";
	private static String createMssSql =
			"CREATE TABLE IF NOT EXISTS `db1`.`movie_schedule_seat` ("
			+ "  `mss_id` INT NOT NULL AUTO_INCREMENT,"
			+ "  `mss_seat_available` TINYINT(1) NULL,"
			+ "  `movie_schedule_ms_id` INT NOT NULL,"
			+ "  `seat_seat_id` INT NOT NULL,"
			+ "  PRIMARY KEY (`mss_id`),"
			+ "  INDEX `fk_movie_schedule_seat_movie_schedule1_idx` (`movie_schedule_ms_id` ASC) VISIBLE,"
			+ "  INDEX `fk_movie_schedule_seat_seat1_idx` (`seat_seat_id` ASC) VISIBLE,"
			+ "  CONSTRAINT `fk_movie_schedule_seat_movie_schedule1`"
			+ "    FOREIGN KEY (`movie_schedule_ms_id`)"
			+ "    REFERENCES `db1`.`movie_schedule` (`ms_id`)"
			+ "    ON DELETE CASCADE"
			+ "    ON UPDATE CASCADE,"
			+ "  CONSTRAINT `fk_movie_schedule_seat_seat1`"
			+ "    FOREIGN KEY (`seat_seat_id`)"
			+ "    REFERENCES `db1`.`seat` (`seat_id`)"
			+ "    ON DELETE CASCADE"
			+ "    ON UPDATE CASCADE)"
			+ "ENGINE = InnoDB;";
	
	private static String createUserAccountSql = "create user IF NOT EXISTS 'user1'@'%' identified by 'user1';";
	private static String grantUserAccountSql = "grant all privileges on db1.* to user1@'%';";
	
	public static void ExecuteInitSql() throws SQLException {
		stmt = JdbcConnect.conn.createStatement();
		try {
			
			stmt.executeUpdate(dropMssSql);
			stmt.executeUpdate(dropReservationSql);
			stmt.executeUpdate(dropTicketSql);
			stmt.executeUpdate(dropMsSql);
			stmt.executeUpdate(dropSeatSql);
			stmt.executeUpdate(dropUserSql);
			stmt.executeUpdate(dropMovieSql);
			stmt.executeUpdate(dropTheaterSql);
			
			
			stmt.execute(createUserSql);
			stmt.execute(createMovieSql);
			stmt.execute(createTheaterSql);
			stmt.execute(createSeatSql);
			stmt.execute(createMsSql);
			stmt.execute(createMssSql);
			stmt.execute(createTicketSql);
			stmt.execute(createReservationSql);
			
			stmt.execute(createUserAccountSql);
			stmt.execute(grantUserAccountSql);
			
			initUserData();
			initMovieData();
			initTheatherData();
			initSeatData();
			initMovieScheduleData();
			initMovieScheduleSeatData();
			initTicketData();
			initReservationData();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<HashMap<String, Object>> findAllByName(String name) throws SQLException {
		try {
			String sql = "SELECT * FROM " + name;
			stmt = JdbcConnect.conn.createStatement();
			rs = stmt.executeQuery(sql);
			

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		
			while(rs.next()) {
				HashMap<String, Object> row = new HashMap<>(columns);
				for(int i=1;i<=columns;i++) {
					row.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(row);
			}
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ArrayList<HashMap<String, Object>> findMovieByName(String name,String keyword) throws SQLException {
		try {
			String sql = "SELECT * FROM movie WHERE " + name + " LIKE ?";
			pstmt = JdbcConnect.conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		
			while(rs.next()) {
				HashMap<String, Object> row = new HashMap<>(columns);
				for(int i=1;i<=columns;i++) {
					row.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(row);
			}
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void executeQuery(String sql) {
		try {
			stmt = JdbcConnect.conn.createStatement();
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(null, "SQL이 정상적으로 실행되었습니다.");
			System.out.println("성공적인 "+sql);
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "SQL 오류가 발생했습니다", "SQL 오류", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.out.println("실패 ! : " + sql);
			
		}
	}
}
