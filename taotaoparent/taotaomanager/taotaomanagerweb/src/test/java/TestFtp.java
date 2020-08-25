import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestFtp {
    public void testFtpClient() throws IOException {
//        创建一个FtpClient对象
        FTPClient ftpClient = new FTPClient();
        //创建一个FtpClient连接 默认是21端口
        ftpClient.connect("192.168.109.128",21);
//        登录FtpClient
        ftpClient.login("ftpuser","admin");
        String path = "C:\\Users\\RNGNB\\Desktop\\img\\5a040603Naf5cfaef.jpg";
//        上传文件
        //读取本地文件
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        //设置上传路径
        ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
        //修改上传文件的格式
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //第一个参数：服务器端文档名
        //第二个参数：上传文档的fileInputStream
        ftpClient.storeFile("hello.jpg",fileInputStream);
//        关闭连接
        ftpClient.logout();
    }
}
