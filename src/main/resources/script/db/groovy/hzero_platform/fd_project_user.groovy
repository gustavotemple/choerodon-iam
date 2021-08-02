package script.db.groovy.hzero_platform

databaseChangeLog(logicalFilePath: 'script/db/fd_project_user.groovy') {
    changeSet(author: 'scp', id: '2020-04-16-fd-project-user') {
        createTable(tableName: "FD_PROJECT_USER") {
            column(name: 'ID', type: 'BIGINT UNSIGNED', autoIncrement: true, remarks: '表ID，主键，供其他表做外键，unsigned bigint、单表时自增、步长为 1') {
                constraints(primaryKey: true, primaryKeyName: 'PK_FD_PROJECT')
            }
            column(name: 'PROJECT_ID', type: 'BIGINT UNSIGNED', remarks: '项目id') {
                constraints(nullable: false)
            }
            column(name: 'MEMBER_ROLE_ID', type: 'BIGINT UNSIGNED', remarks: '用户角色id') {
                constraints(nullable: false)
            }

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT UNSIGNED", defaultValue: "1")
            column(name: "CREATED_BY", type: "BIGINT UNSIGNED", defaultValue: "0")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT UNSIGNED", defaultValue: "0")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
        addUniqueConstraint(tableName: 'FD_PROJECT_USER', columnNames: 'PROJECT_ID, MEMBER_ROLE_ID', constraintName: 'UK_FD_PROJECT_USER_ROLE')

    }

    changeSet(author: 'scp', id: '2020-11-03-rename-table') {
        renameTable(newTableName: 'fd_project_permission', oldTableName: 'fd_project_user')
    }
    changeSet(author: 'scp', id: '2021-06-18-add-index') {
        createIndex(indexName: 'U1_PERMISSION_PROJECT_ID', tableName: 'fd_project_permission') {
            column(name: 'PROJECT_ID')
        }
        createIndex(indexName: 'U1_PERMISSION_MEMBER_ROLE_ID', tableName: 'fd_project_permission') {
            column(name: 'MEMBER_ROLE_ID')
        }
    }
}