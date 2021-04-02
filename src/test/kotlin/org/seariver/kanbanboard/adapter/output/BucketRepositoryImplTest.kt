package org.seariver.kanbanboard.adapter.output

import helper.DataSourceHelper
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
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
            .hasSize(2)
            .extracting("position", "name")
            .contains(
                Assertions.tuple(200.987, "SECOND-BUCKET"),
                Assertions.tuple(100.15, "FIRST-BUCKET")
            )
    }
}
