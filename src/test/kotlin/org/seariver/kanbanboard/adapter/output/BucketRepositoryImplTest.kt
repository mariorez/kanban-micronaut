package org.seariver.kanbanboard.adapter.output

import helper.DataSourceHelper
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.seariver.kanbanboard.application.domain.Bucket
import org.seariver.kanbanboard.application.output.BucketRepository
import java.util.*
import javax.sql.DataSource

class BucketRepositoryImplTest {

    private val repository = BucketRepositoryImpl(DataSourceHelper())

    @Test
    fun `MUST be an instance of BucketRepository`() {
        assertThat(BucketRepositoryImpl(mock<DataSource>())).isInstanceOf(BucketRepository::class.java)
    }

    @Test
    fun `GIVEN valid bucket entity MUST persist in database successful`() {

        // GIVEN
        val bucketId = UUID.randomUUID()
        val position = 1.5
        val name = "TODO"
        val bucket = Bucket(bucketId = bucketId, position = position, name = name)

        // WHEN
        repository.create(bucket)

        // THEN
        val actualBucket = repository.findById(bucketId).get()
        assertThat(actualBucket.bucketId).isEqualTo(bucketId)
        assertThat(actualBucket.position).isEqualTo(position)
        assertThat(actualBucket.name).isEqualTo(name)
    }

    @Test
    fun `WHEN find all buckets MUST return the result set`() {

        // WHEN
        val result = repository.findAll()

        // THEN
        assertThat(result)
            .hasSize(10)
            .extracting("position", "name")
            .startsWith(
                tuple(10.15, "FIRST-BUCKET"),
                tuple(20.987, "SECOND-BUCKET"),
                tuple(30.1, "BUCKET-1")
            )
    }
}
