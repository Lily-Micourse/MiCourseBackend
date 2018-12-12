package org.lily.micourse.config.data

import org.hibernate.dialect.MySQL57Dialect

/**
 * Created on 12/12/2018.
 * Description:
 * @author iznauy
 */
class MySQL5DialectUTF8: MySQL57Dialect() {

    override fun getTableTypeString(): String = " ENGINE=InnoDB DEFAULT CHARSET=utf8"

}