-- Create Categories table
CREATE TABLE Categories (
                            CategoryID INT PRIMARY KEY IDENTITY(1,1),
                            CategoryName NVARCHAR(100) NOT NULL
);

-- Create Tasks table
CREATE TABLE Tasks (
                       TaskID INT PRIMARY KEY IDENTITY(1,1),
                       Description NVARCHAR(255) NOT NULL,
                       CategoryID INT,
                       Date DATE NOT NULL,
                       StartTime DATETIME,
                       EndTime DATETIME,
                       Status NVARCHAR(50) NOT NULL,
                       CreateTime DATETIME DEFAULT GETDATE(),
                       FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

-- Create TaskStatistics table
CREATE TABLE TaskStatistics (
                                Date DATE NOT NULL,
                                CategoryID INT NOT NULL,
                                CompletedTasks INT DEFAULT 0,
                                UncompletedTasks INT DEFAULT 0,
                                TotalTimeSpent INT DEFAULT 0, -- time in minutes
                                PRIMARY KEY (Date, CategoryID),
                                FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

-- Create DailyLimit table
CREATE TABLE DailyLimit (
                            Date DATE PRIMARY KEY,
                            TaskLimit INT NOT NULL
);

-- Create AddNewTask stored procedure
CREATE PROCEDURE AddNewTask
    @Description NVARCHAR(255),
    @CategoryID INT,
    @Date DATE,
    @StartTime DATETIME,
    @EndTime DATETIME,
    @Status NVARCHAR(50)
AS
BEGIN
INSERT INTO Tasks (Description, CategoryID, Date, StartTime, EndTime, Status)
VALUES (@Description, @CategoryID, @Date, @StartTime, @EndTime, @Status)
END

-- Create DeleteTask stored procedure
CREATE PROCEDURE DeleteTask
    @TaskID INT
AS
BEGIN
DELETE FROM Tasks WHERE TaskID = @TaskID;
END

-- Create UpdateTaskTime stored procedure
CREATE PROCEDURE UpdateTaskTime
    @TaskID INT,
    @StartTime DATETIME = NULL,
    @EndTime DATETIME = NULL
AS
BEGIN
UPDATE Tasks
SET StartTime = ISNULL(@StartTime, StartTime),
    EndTime = ISNULL(@EndTime, EndTime)
WHERE TaskID = @TaskID;
END

-- Create AssignCategoryToTask stored procedure
CREATE PROCEDURE AssignCategoryToTask
    @TaskID INT,
    @CategoryID INT
AS
BEGIN
UPDATE Tasks
SET CategoryID = @CategoryID
WHERE TaskID = @TaskID;
END

-- Create GetTaskSummaryByCategory function
CREATE FUNCTION GetTaskSummaryByCategory()
    RETURNS TABLE
    AS
RETURN (
    SELECT
        CategoryID,
        SUM(CompletedTasks) AS TotalCompletedTasks,
        SUM(UncompletedTasks) AS TotalUncompletedTasks,
        SUM(TotalTimeSpent) AS TotalTimeSpent
    FROM
        TaskStatistics
    GROUP BY
        CategoryID
);

-- Create SetDailyLimit stored procedure
CREATE PROCEDURE SetDailyLimit
    @Date DATE,
    @TaskLimit INT
AS
BEGIN
    IF EXISTS (SELECT * FROM DailyLimit WHERE Date = @Date)
BEGIN
UPDATE DailyLimit
SET TaskLimit = @TaskLimit
WHERE Date = @Date
END
ELSE
BEGIN
INSERT INTO DailyLimit (Date, TaskLimit)
VALUES (@Date, @TaskLimit)
END
END

-- Sample data for Categories
INSERT INTO Categories (CategoryName) VALUES
                                          ('Work'),
                                          ('Personal'),
                                          ('Study'),
                                          ('Exercise'),
                                          ('Hobbies'),
                                          ('Meetings');

-- Sample data for Tasks
INSERT INTO Tasks (Description, CategoryID, Date, StartTime, EndTime, Status)
VALUES
    ('Complete project report', 1, '2023-01-10', '2023-01-10 09:00:00', '2023-01-10 11:00:00', 'Completed'),
    ('Grocery shopping', 2, '2023-01-10', NULL, NULL, 'Pending'),
    ('Study for exam', 3, '2023-01-10', '2023-01-10 14:00:00', '2023-01-10 16:00:00', 'Completed'),
    ('Morning Jog', 4, '2023-01-11', '2023-01-11 06:00:00', '2023-01-11 07:00:00', 'Completed'),
    ('Painting session', 5, '2023-01-11', '2023-01-11 10:00:00', '2023-01-11 12:00:00', 'Completed'),
    ('Client Meeting', 6, '2023-01-12', '2023-01-12 15:00:00', '2023-01-12 16:00:00', 'Scheduled'),
    ('Team Meeting', 6, '2023-01-12', '2023-01-12 17:00:00', '2023-01-12 18:00:00', 'Scheduled'),
    ('Prepare Dinner', 2, '2023-01-10', '2023-01-10 17:00:00', '2023-01-10 18:00:00', 'Pending');

-- Sample data for TaskStatistics
INSERT INTO TaskStatistics (Date, CategoryID, CompletedTasks, UncompletedTasks, TotalTimeSpent)
VALUES
    ('2023-01-09', 4, 1, 0, 60),
    ('2023-01-09', 5, 1, 0, 60),
    ('2023-01-09', 6, 0, 2, 0),
    ('2023-01-10', 1, 1, 0, 120),
    ('2023-01-10', 2, 0, 1, 0),
    ('2023-01-10', 3, 1, 0, 60),
    ('2023-01-11', 4, 0, 1, 0),
    ('2023-01-11', 5, 1, 0, 60),
    ('2023-01-11', 6, 2, 0, 120);

-- Sample data for DailyLimit
INSERT INTO DailyLimit (Date, TaskLimit)
VALUES
    ('2023-01-10', 5),
    ('2023-01-09', 3),
    ('2023-01-12', 6);