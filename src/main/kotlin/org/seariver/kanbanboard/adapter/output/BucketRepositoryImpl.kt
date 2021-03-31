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

        connection.prepareStatement(sql).run {
            setString(1, bucket.bucketId.toString())
            setDouble(2, bucket.position)
            setString(3, bucket.name)
            executeUpdate()
        }
    }

    override fun findById(bucketId: UUID): Optional<Bucket> {

        val sql = """
            SELECT bucket_id, position, name 
            FROM bucket 
            WHERE bucket_id = ?
            """

        return connection.prepareStatement(sql).run {
            setString(1, bucketId.toString())
            executeQuery().run {
                if (next()) Optional.of(
                    Bucket(
                        UUID.fromString(getString("bucket_id")),
                        getDouble("position"),
                        getString("name")
                    )
                ) else Optional.empty()
            }
        }
    }
}
