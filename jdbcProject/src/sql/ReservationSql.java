package sql;

public class ReservationSql {
	public static int RESERVATION_SQL_MAX = 12;
	public static String reservation1Sql = "insert into reservation values\r\n"
			+ "	(1,'애플페이','완료',16000,'2023-05-25 14:30:00',1,1,1);";
	public static String reservation2Sql = "insert into reservation values\r\n"
			+ "	(2,'애플페이','완료',16000,'2023-05-11 14:30:00',1,3,2);";
	public static String reservation3Sql = "insert into reservation values\r\n"
			+ "	(3,'네이버페이','완료',16000,'2023-05-16 14:30:00',1,5,3);";
	public static String reservation4Sql = "insert into reservation values\r\n"
			+ "	(4,'카카오페이','완료',16000,'2023-05-22 14:30:00',1,7,4);";
	public static String reservation5Sql = "insert into reservation values\r\n"
			+ "	(5,'애플페이','완료',16000,'2023-05-21 14:30:00',1,9,5);";
	public static String reservation6Sql = "insert into reservation values\r\n"
			+ "	(6,'애플페이','완료',16000,'2023-05-19 14:30:00',1,11,6);";
	public static String reservation7Sql = "insert into reservation values\r\n"
			+ "	(7,'애플페이','완료',16000,'2023-04-23 14:30:00',1,13,7);";
	public static String reservation8Sql = "insert into reservation values\r\n"
			+ "	(8,'애플페이','완료',16000,'2023-05-01 14:30:00',1,15,8);";
	public static String reservation9Sql = "insert into reservation values\r\n"
			+ "	(9,'애플페이','완료',16000,'2023-05-12 14:30:00',1,17,9);";
	public static String reservation10Sql = "insert into reservation values\r\n"
			+ "	(10,'애플페이','완료',16000,'2023-05-13 14:30:00',1,19,10);";
	public static String reservation11Sql = "insert into reservation values\r\n"
			+ "	(11,'애플페이','완료',16000,'2023-05-14 14:30:00',1,21,11);";
	public static String reservation12Sql = "insert into reservation values\r\n"
			+ "	(12,'애플페이','완료',16000,'2023-05-15 14:30:00',1,23, 12);";
	
	public static String deleteReservationByIdSql = "DELETE FROM reservation" + " WHERE r_id = ";
}
