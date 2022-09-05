package script.db

/**
 *
 * @author dinghuang123@gmail.com
 * @since 2018/10/9
 */
databaseChangeLog(logicalFilePath: 'script/db/iam_work_calendar_holiday_ref.groovy') {
    changeSet(id: '2019-07-30-iam-work-calendar-holiday-ref', author: 'jiameng.cao@hand-china.com') {
        createTable(tableName: "iam_work_calendar_holiday_ref", remarks: "工作日历节假日表") {
            column(name: 'calendar_id', type: 'BIGINT UNSIGNED', autoIncrement: true, remarks: '主键') {
                constraints(primaryKey: true)
            }
            column(name: 'name', type: 'VARCHAR(30)', remarks: '节假日名称')
            column(name: 'holiday', type: 'VARCHAR(11)', remarks: '日期') {
                constraints(nullable: false)
            }
            column(name: 'status', type: 'tinyint(1)', remarks: '状态，0为放假，1为补班') {
                constraints(nullable: false)
            }
            column(name: 'year', type: 'int(5)', remarks: '年份') {
                constraints(nullable: false)
            }
            column(name: "object_version_number", type: "BIGINT UNSIGNED", defaultValue: "1")
            column(name: "created_by", type: "BIGINT UNSIGNED", defaultValue: "0")
            column(name: "creation_date", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "last_updated_by", type: "BIGINT UNSIGNED", defaultValue: "0")
            column(name: "last_update_date", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
        createIndex(indexName: 'uk_holiday', tableName: 'iam_work_calendar_holiday_ref', unique: true) {
            column(name: 'holiday')
        }
    }
}