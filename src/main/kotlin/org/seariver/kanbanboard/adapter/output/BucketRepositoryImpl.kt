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
            values (?, ?, ?)
            """.trimIndent()

        with(connection) {
            val statement = prepareStatement(sql)
            statement.setString(1, bucket.bucketId.toString())
            statement.setDouble(2, bucket.position)
            statement.setString(3, bucket.name)
            statement.execute()
        }
    }

    override fun findById(bucketId: UUID): Optional<Bucket> {

        val sql = """
            SELECT bucket_id, position, name 
            FROM bucket 
            WHERE bucket_id = ?
            """.trimIndent()

        with(connection) {
            val statement = prepareStatement(sql)
            statement.setString(1, bucketId.toString())

            val rs = statement.executeQuery()

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
