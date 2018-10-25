# JDBC
JDBC代表Java数据库连接(Java Database Connectivity)
它是用于Java编程语言和数据库之间的数据库无关连接的标准Java API
换句话说：JDBC是用于在Java语言编程中与数据库连接的API。

JDBC API提供以下接口和类 
- DriverManager：此类管理数据库驱动程序列表。 使用通信子协议将来自java应用程序的连接请求与适当的数据库驱动程序进行匹配。在JDBC下识别某个子协议的第一个驱动程序将用于建立数据库连接。
- Driver：此接口处理与数据库服务器的通信。我们很少会直接与Driver对象进行交互。 但会使用DriverManager对象来管理这种类型的对象。 它还提取与使用Driver对象相关的信息。
- Connection：此接口具有用于联系数据库的所有方法。 连接(Connection)对象表示通信上下文，即，与数据库的所有通信仅通过连接对象。Statement：使用从此接口创建的对象将SQL语句提交到数据库。 除了执行存储过程之外，一些派生接口还接受参数。
- ResultSet：在使用Statement对象执行SQL查询后，这些对象保存从数据库检索的数据。 它作为一个迭代器并可移动ResultSet对象查询的数据。
- SQLException：此类处理数据库应用程序中发生的任何错误。
  

## 一、SQL语句
- 1、创建数据库
- 2、删除数据库
- 3、创建表
- 4、删除表
- 5、查询数据
- 6、更新数据
    - 增加数据
    - 删除数据
    - 插入数据
    - 修改数据

```sql实例
        -- 创建数据库
        CREATE DATABASE test
        -- 删除数据库
        DROP DATABASE test;
        -- 创建表
        CREATE TABLE student
        (
                id INT NOT NULL,
                studentName VARCHAR(255) NOT NULL,
                age INT,
                birth date,
                grade FLOAT,
        -- 		主键
                PRIMARY KEY (id)
        );
        -- 删除表
        DROP TABLE student;
        -- 查询数据
        SELECT	*
        FROM student
        WHERE age = 12;
        -- 更新数据
        -- 1、增
        INSERT INTO student VALUES(1,"四火",12,"1998-01-01",99);
        -- 2、删
        DELETE 
        FROM student
        WHERE birth = "1998-01-01";
        -- 3、查
        SELECT	*
        FROM student
        WHERE age = 18;
        -- 4、改
        UPDATE student 
        SET age = 18
        WHERE id = 1;

```

### SQL数据类型
- Text
    - char
    - varchar   可变长字符串
    - tinytext
    - text
- Number
    - tinyint
    - smallint
    - int
    - float
    - double
- Date/Time
    - date      XXXX-XX-XX
    - datetime  XXXX-XX-XX XX：XX：XX
    - time
    - year
    - timestamp 时间戳
  
## 二、创建JDBC程序
构建JDBC应用程序涉及以下六个步骤 
- **导入包**：需要包含包含数据库编程所需的JDBC类的包。 大多数情况下，使用import java.sql.*就足够了。
- **注册JDBC驱动程序**：需要初始化驱动程序，以便可以打开与数据库的通信通道。
- **打开一个连接**：需要使用DriverManager.getConnection()方法创建一个Connection对象，它表示与数据库的物理连接。
- **执行查询**：需要使用类型为Statement的对象来构建和提交SQL语句到数据库。
- **从结果集中提取数据**：需要使用相应的ResultSet.getXXX()方法从结果集中检索数据。
- **清理环境**：需要明确地关闭所有数据库资源，而不依赖于JVM的垃圾收集。

```示例
    //STEP 1. Import required packages
    import java.sql.*;

    public class FirstExample {
        // JDBC driver name and database URL
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
        static final String DB_URL = "jdbc:mysql://localhost/emp";

        //  Database credentials
        static final String USER = "root";
        static final String PASS = "123456";

        public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, first, last, age FROM Employees";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("There are so thing wrong!");
        }//end main
    }//end FirstExample
```