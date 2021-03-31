package org.seariver.kanbanboard.adapter.output

import org.seariver.kanbanboard.application.domain.Bucket
import org.seariver.kanbanboard.application.output.BucketRepository
import java.util.*
import javax.sql.DataSource

class BucketRepositoryImpl(private val datasource: DataSource) : BucketRepository {

    private val connection = datasource.connection

    override fun create(bucket: Bucket) {

        val sql = """
            INSERT INTO bucket (bucket_id, position, name) 
            values ('${bucket.bucketId}', '${bucket.position}', '${bucket.name}')
            """.trimIndent()

        with(connection) {
            createStatement().execute(sql)
            commit()
        }
    }

    override fun findById(bucketId: UUID): Optional<Bucket> {

        val sql = """
            SELECT bucket_id, position, name 
            FROM bucket 
            WHERE bucket_id = '$bucketId'
            """.trimIndent()

        with(connection) {
            val rs = createStatement().executeQuery(sql)
            return if (rs.next()) Optional.of(
                Bucket(
                    UUID.fromString(rs.getString("bucket_id")),
                    rs.getDouble("position"),
                    rs.getString("name")
                )
            ) else Optional.empty()
        }
    }
}
