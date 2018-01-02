package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DaoHelper {
    /*
     * ��TOMCAT����Դ�õ����Ӷ���
     */
    Connection getConnection();

    /*
     * �ر�Connection����,�����ݿ����ӷŻص����ӳ���
     */
    void closeConnection(Connection con);

    /*
     * �ر�PreparedStatement����
     */
    void closePreparedStatement(PreparedStatement stmt);

    /*
     * �ر�ResultSet����
     */
    void closeResult(ResultSet result);
}
