package script.db.groovy.hzero_platform

databaseChangeLog(logicalFilePath: 'script/db/fd_star_project_user_rel.groovy') {
    changeSet(author: 'wh', id: '2020-06-11-fd-star-project-user-rel.groovy') {
        createTable(tableName: "fd_star_project_user_rel", remarks: "用户标星项目关系表") {
            column(name: 'ID', type: 'BIGINT UNSIGNED', autoIncrement: true, remarks: '表ID，主键，供其他表做外键，unsigned bigint、单表时自增、步长为 1') {
                constraints(primaryKey: true, primaryKeyName: 'PK_FD_PROJECT')
            }
            column(name: 'PROJECT_ID', type: 'BIGINT UNSIGNED', remarks: '项目id') {
                constraints(nullable: false)
            }
            column(name: 'USER_ID', type: 'BIGINT UNSIGNED', remarks: '用户id') {
                constraints(nullable: false)
            }

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT UNSIGNED", defaultValue: "1")
            column(name: "CREATED_BY", type: "BIGINT UNSIGNED", defaultValue: "0")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT UNSIGNED", defaultValue: "0")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
        addUniqueConstraint(tableName: 'fd_star_project_user_rel', columnNames: 'PROJECT_ID, USER_ID', constraintName: 'UK_FD_STAR_PROJECT_USER_ROLE')

    }

    changeSet(author: 'xiangwang04@hand-china.com', id: '2020-10-14-add-column') {
        addColumn(tableName: 'fd_star_project_user_rel') {
            column(name: 'SORT', type: 'BIGINT UNSIGNED', remarks: '排序序号', afterColumn: 'USER_ID') {
                constraints(nullable: false)
            }
            column(name: 'ORGANIZATION_ID', type: 'BIGINT UNSIGNED', remarks: '组织id', afterColumn: 'PROJECT_ID') {
                constraints(nullable: false)
            }
        }
    }
}