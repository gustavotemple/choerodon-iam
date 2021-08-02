package script.db.groovy.hzero_platform

databaseChangeLog(logicalFilePath: 'script/db/fd_user_guide_step.groovy') {
    changeSet(author: 'wanghao', id: '2021-05-18-fd_user_guide_step') {
        createTable(tableName: "FD_USER_GUIDE_STEP") {
            column(name: 'ID', type: 'BIGINT UNSIGNED', autoIncrement: true, remarks: '表ID，主键，供其他表做外键，unsigned bigint、单表时自增、步长为 1') {
                constraints(primaryKey: true, primaryKeyName: 'PK_FD_PROJECT')
            }
            column(name: 'STEP_CODE', type: 'VARCHAR(256)', remarks: '步骤编码') {
                constraints(nullable: false)
            }
            column(name: 'STEP_NAME', type: 'VARCHAR(256)', remarks: '步骤名') {
                constraints(nullable: false)
            }
            column(name: 'DESCRIPTION', type: 'VARCHAR(512)', remarks: '步骤描述') {
                constraints(nullable: false)
            }
            column(name: 'DOC_URL', type: 'VARCHAR(2048)', remarks: '指引文档地址') {
                constraints(nullable: false)
            }

            column(name: 'PAGE_URL', type: 'VARCHAR(2048)', remarks: '页面地址') {
                constraints(nullable: false)
            }

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT UNSIGNED", defaultValue: "1")
            column(name: "CREATED_BY", type: "BIGINT UNSIGNED", defaultValue: "0")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT UNSIGNED", defaultValue: "0")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
        addUniqueConstraint(tableName: 'FD_USER_GUIDE_STEP', columnNames: 'STEP_CODE', constraintName: 'UK_FD_USER_GUIDE_STEP_U1')

    }

    changeSet(author: 'wanghao', id: '2021-04-19-fd-user-guide-step-add-column') {
        addColumn(tableName: 'FD_USER_GUIDE_STEP') {
            column(name: 'PERMISSION_ID', type: "BIGINT UNSIGNED", remarks: '权限集id', afterColumn: 'PAGE_URL')
        }
    }
}