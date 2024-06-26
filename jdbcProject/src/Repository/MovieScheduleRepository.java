package Repository;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
public class MovieScheduleRepository {
	private static Statement stmt;
	private static ResultSet rs;
	
	@SuppressWarnings("null")
	public static ArrayList<HashMap<String, Object>> findMovieScheduleByName(String mName) {
			
		String sql = "SELECT * FROM movie_schedule WHERE movie_m_id = (SELECT m_id FROM movie WHERE m_name = '" + mName + "')";
			
		try {
			
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public static void updateMssSeatAvailable(int msId, int seatId) {
		String sql = "update movie_schedule_seat set mss_seat_available = 1 where movie_schedule_ms_id = "+msId+" and seat_seat_id = "+seatId+";";
		try {
			stmt = JdbcConnect.conn.createStatement();
			stmt.execute(sql);
			System.out.println("updated "+msId + ", seatId: " + seatId);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
