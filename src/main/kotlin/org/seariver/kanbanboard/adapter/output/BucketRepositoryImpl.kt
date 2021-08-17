package org.seariver.kanbanboard.adapter.output

import org.seariver.kanbanboard.application.domain.Bucket
import org.seariver.kanbanboard.application.output.BucketRepository
import java.util.*
import javax.inject.Singleton
import javax.sql.DataSource

@Singleton
class BucketRepositoryImpl(private val datasource: DataSource) : BucketRepository {

    override fun create(bucket: Bucket) {

        val sql = """
            INSERT INTO bucket (bucket_id, position, name) 
            values (?, ?, ?)
            """.trimIndent()

        datasource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setObject(1, bucket.bucketId)
                stmt.setDouble(2, bucket.position)
                stmt.setString(3, bucket.name)
                stmt.executeUpdate()
            }
        }
    }

    override fun findById(bucketId: UUID): Optional<Bucket> {

        val sql = """
            SELECT bucket_id, position, name 
            FROM bucket 
            WHERE bucket_id = ?
            """

        return datasource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, bucketId.toString())
                stmt.executeQuery().use { rs ->
                    if (rs.next()) Optional.of(
                        Bucket(
                            UUID.fromString(rs.getString("bucket_id")),
                            rs.getDouble("position"),
                            rs.getString("name")
                        )
                    ) else Optional.empty()
                }
            }
        }
    }

    override fun findAll(pageSize: Int): Set<Bucket> {

        val sql = """
            SELECT bucket_id, position, name 
            FROM bucket
            ORDER BY position ASC
            LIMIT ?
            """

        val result = mutableSetOf<Bucket>()

        datasource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, pageSize)
                stmt.executeQuery().use { rs ->
                    while (rs.next())
                        result += Bucket(
                            UUID.fromString(rs.getString("bucket_id")),
                            rs.getDouble("position"),
                            rs.getString("name")
                        )
                }
            }
        }

        return Collections.unmodifiableSet(result);
    }
}
