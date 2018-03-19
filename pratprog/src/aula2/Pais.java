package aula2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pais {
	private int id;
	private String nome;
	private long populacao;
	private double area;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private static Pais conexao1;

	public static Pais getInstance() {
		if (conexao1 == null) {
			conexao1 = new Pais();
		}
		return conexao1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getPopulacao() {
		return populacao;
	}

	public void setPopulacao(long populacao) {
		this.populacao = populacao;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public Connection obtemConexao() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/pratprog?useSSL=false", "root", "");
	}

	public Pais maiorHabitante() throws SQLException {
		String sqlSelect = "SELECT * FROM PAIS " + "WHERE populacao = (select max(populacao) from PAIS)";
		Pais pais = null;
		Connection conn = null;
		try {
			conn = obtemConexao();
			PreparedStatement stm = conn.prepareStatement(sqlSelect);
			{
				stm.execute();
				ResultSet resultSet = stm.executeQuery();

				while (resultSet.next()) {
					pais = new Pais();
					pais.setArea(resultSet.getDouble("area"));
					pais.setNome(resultSet.getString("nome"));
					pais.setId(resultSet.getInt("id"));
					pais.setPopulacao(resultSet.getLong("populacao"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			//se a conexao for diferente de nulo fecha
			if(conn!=null) {
				conn.close();	
			}
			
		}
		return pais; 
	}

	public Pais menorArea() throws SQLException {
		String sqlSelect = "SELECT * FROM PAIS\r\n" + "WHERE area = (select min(area) from PAIS)";
		Pais pais = null;
		Connection conn = null;
		try {
			conn = obtemConexao();
			PreparedStatement stm = conn.prepareStatement(sqlSelect);
			{
				stm.execute();
				ResultSet resultSet = stm.executeQuery();

				while (resultSet.next()) {

					pais = new Pais();
					pais.setArea(resultSet.getDouble("area"));
					pais.setNome(resultSet.getString("nome"));
					pais.setId(resultSet.getInt("id"));
					pais.setPopulacao(resultSet.getLong("populacao"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		
			if(conn!=null) {
				conn.close();	
			}
			
		}

		return pais;

	}

	public Pais[] paises3() throws SQLException {
		String sqlSelect = "SELECT * FROM PAIS LIMIT 3";
		Pais paises[] = new Pais[3];

		Connection conn = null;
		try {
			conn = obtemConexao();
			PreparedStatement stm = conn.prepareStatement(sqlSelect);
			{
				stm.execute();
				ResultSet resultSet = stm.executeQuery();

				int contador = 0;
			    while (resultSet.next()) {

					Pais pais = new Pais();
					pais.setArea(resultSet.getDouble("area"));
					pais.setNome(resultSet.getString("nome"));
					pais.setId(resultSet.getInt("id"));
					pais.setPopulacao(resultSet.getLong("populacao"));

					paises[contador] = pais;
					contador++;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			
			if(conn!=null) {
				conn.close();	
			}
			
		}

		return paises;

	}

	}
