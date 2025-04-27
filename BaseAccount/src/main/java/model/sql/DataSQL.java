package model.sql;

public class DataSQL {

    public static final String INIT_DATA =
                    "DROP TABLE IF EXISTS transactions;\n" +
                    "DROP TABLE IF EXISTS accounts;\n" +
                    "DROP TABLE IF EXISTS customers;\n" +
                    "\n" +
                    "-- customers 테이블 생성\n" +
                    "CREATE TABLE customers (\n" +
                    "    customer_id INT PRIMARY KEY AUTO_INCREMENT,  -- 고객 ID (자동 증가)\n" +
                    "    name VARCHAR(100) NOT NULL,                   -- 고객 이름\n" +
                    "    email VARCHAR(100) NOT NULL,                  -- 고객 이메일\n" +
                    "    phone VARCHAR(20),                            -- 고객 전화번호\n" +
                    "    address VARCHAR(255),                         -- 고객 주소\n" +
                    "    date_of_birth DATE                           -- 고객 생년월일\n" +
                    ");\n" +
                    "\n" +
                    "-- customers 테이블에 10개의 샘플 데이터 삽입\n" +
                    "INSERT INTO customers (name, email, phone, address, date_of_birth) VALUES\n" +
                    "('John Doe', 'john.doe@example.com', '010-1234-5678', 'Seoul, Korea', '1980-01-01'),\n" +
                    "('Jane Smith', 'jane.smith@example.com', '010-9876-5432', 'Busan, Korea', '1990-05-15'),\n" +
                    "('Robert Johnson', 'robert.johnson@example.com', '010-2345-6789', 'Incheon, Korea', '1985-11-23'),\n" +
                    "('Emily Davis', 'emily.davis@example.com', '010-3456-7890', 'Daegu, Korea', '1995-02-07'),\n" +
                    "('Michael Wilson', 'michael.wilson@example.com', '010-4567-8901', 'Gwangju, Korea', '1982-07-19'),\n" +
                    "('Sarah Brown', 'sarah.brown@example.com', '010-5678-9012', 'Daejeon, Korea', '1988-09-30'),\n" +
                    "('David Lee', 'david.lee@example.com', '010-6789-0123', 'Ulsan, Korea', '1992-03-25'),\n" +
                    "('Jennifer Kim', 'jennifer.kim@example.com', '010-7890-1234', 'Suwon, Korea', '1987-12-14'),\n" +
                    "('Thomas Park', 'thomas.park@example.com', '010-8901-2345', 'Jeju, Korea', '1994-06-08'),\n" +
                    "('Lisa Choi', 'lisa.choi@example.com', '010-9012-3456', 'Sejong, Korea', '1991-04-27');\n" +
                    "\n" +
                    "-- accounts 테이블 생성\n" +
                    "CREATE TABLE accounts (\n" +
                    "    account_id INT PRIMARY KEY AUTO_INCREMENT,   -- 계좌 ID (자동 증가)\n" +
                    "    customer_id INT,                             -- 고객 ID (foreign key)\n" +
                    "    account_type VARCHAR(20) NOT NULL,           -- 계좌 종류 (예: 기본, 예금, 적금 등)\n" +
                    "    balance DECIMAL(15, 2) DEFAULT 0.00,         -- 계좌 잔액\n" +
                    "    date_opened DATE NOT NULL,                   -- 계좌 개설일\n" +
                    "    status VARCHAR(20) DEFAULT 'ACTIVE',         -- 계좌 상태 (예: ACTIVE, CLOSED 등)\n" +
                    "    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE\n" +
                    ");\n" +
                    "\n" +
                    "-- accounts 테이블에 10개의 샘플 데이터 삽입\n" +
                    "INSERT INTO accounts (customer_id, account_type, balance, date_opened, status) VALUES\n" +
                    "(1, 'Checking', 1000.00, '2023-01-01', 'ACTIVE'),\n" +
                    "(1, 'Saving', 5000.00, '2023-02-15', 'ACTIVE'),\n" +
                    "(2, 'Checking', 2000.00, '2023-03-20', 'ACTIVE'),\n" +
                    "(3, 'Saving', 3000.00, '2023-04-01', 'ACTIVE'),\n" +
                    "(4, 'Checking', 1500.00, '2023-05-10', 'ACTIVE'),\n" +
                    "(5, 'Saving', 7500.00, '2023-06-20', 'ACTIVE'),\n" +
                    "(6, 'Checking', 3200.00, '2023-07-15', 'ACTIVE'),\n" +
                    "(7, 'Saving', 10000.00, '2023-08-05', 'ACTIVE'),\n" +
                    "(8, 'Checking', 4500.00, '2023-09-12', 'ACTIVE'),\n" +
                    "(9, 'Saving', 8000.00, '2023-10-30', 'ACTIVE');\n" +
                    "\n" +
                    "-- transactions 테이블 생성\n" +
                    "CREATE TABLE transactions (\n" +
                    "    transaction_id INT PRIMARY KEY AUTO_INCREMENT,  -- 거래 ID (자동 증가)\n" +
                    "    account_id INT,                                 -- 계좌 ID (foreign key)\n" +
                    "    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 거래 일시\n" +
                    "    transaction_type VARCHAR(20) NOT NULL,           -- 거래 종류 (예: 'DEPOSIT', 'WITHDRAWAL')\n" +
                    "    amount DECIMAL(15, 2) NOT NULL,                  -- 거래 금액\n" +
                    "    balance_after DECIMAL(15, 2),                    -- 거래 후 잔액\n" +
                    "    description VARCHAR(255),                        -- 거래 설명 (선택 사항)\n" +
                    "    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE\n" +
                    ");\n" +
                    "\n" +
                    "-- transactions 테이블에 10개의 샘플 데이터 삽입\n" +
                    "INSERT INTO transactions (account_id, transaction_date, transaction_type, amount, balance_after, description) VALUES\n" +
                    "(1, '2023-03-15 10:30:00', 'DEPOSIT', 500.00, 1500.00, '월급 입금'),\n" +
                    "(1, '2023-04-20 15:45:00', 'WITHDRAWAL', 200.00, 1300.00, 'ATM 출금'),\n" +
                    "(2, '2023-05-01 09:15:00', 'DEPOSIT', 1000.00, 6000.00, '보너스 입금'),\n" +
                    "(3, '2023-06-10 14:20:00', 'WITHDRAWAL', 500.00, 1500.00, '쇼핑 결제'),\n" +
                    "(4, '2023-07-05 11:10:00', 'DEPOSIT', 300.00, 3300.00, '이자 입금'),\n" +
                    "(5, '2023-08-12 16:30:00', 'WITHDRAWAL', 700.00, 800.00, '공과금 납부'),\n" +
                    "(6, '2023-09-20 13:25:00', 'DEPOSIT', 2000.00, 9500.00, '투자 수익금'),\n" +
                    "(7, '2023-10-15 10:45:00', 'WITHDRAWAL', 1500.00, 1700.00, '가전제품 구매'),\n" +
                    "(8, '2023-11-03 09:00:00', 'DEPOSIT', 600.00, 5100.00, '환불금 입금'),\n" +
                    "(9, '2023-12-25 17:15:00', 'WITHDRAWAL', 1000.00, 7000.00, '선물 구매');";
}
