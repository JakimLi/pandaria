DROP TABLE IF EXISTS all_data_types;

CREATE TABLE `all_data_types` (
    `varchar` VARCHAR( 20 )  ,
    `tinyint` TINYINT  ,
    `text` TEXT  ,
    `date` DATE  ,
    `smallint` SMALLINT  ,
    `mediumint` MEDIUMINT  ,
    `int` INT  ,
    `bigint` BIGINT  ,
    `float` FLOAT( 10, 2 )  ,
    `double` DOUBLE  ,
    `decimal` DECIMAL( 10, 2 )  ,
    `datetime` DATETIME  ,
    `timestamp` TIMESTAMP  ,
    `time` TIME  ,
    `year` YEAR  ,
    `char` CHAR( 10 )  ,
    `tinyblob` TINYBLOB  ,
    `tinytext` TINYTEXT  ,
    `blob` BLOB  ,
    `mediumblob` MEDIUMBLOB  ,
    `mediumtext` MEDIUMTEXT  ,
    `longblob` LONGBLOB  ,
    `longtext` LONGTEXT  ,
    `enum` ENUM( '1', '2', '3' )  ,
    `set` SET( '1', '2', '3' )  ,
    `bool` BOOL  ,
    `binary` BINARY( 20 )  ,
    `varbinary` VARBINARY( 20 )
);

INSERT INTO all_data_types(`varchar`, `tinyint`, `text`, `date`) values(
'varchar', 3, 'text', '2008-10-10'
);
