package org.seariver.kanbanboard.adapter.output

import helper.DataSourceHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.seariver.kanbanboard.application.domain.Bucket
import org.seariver.kanbanboard.application.output.BucketRepository
import java.util.*
import javax.sql.DataSource

class BucketRepositoryImplTest {

    @Test
    fun `MUST be an instance of BucketRepository`() {
        assertThat(BucketRepositoryImpl(mock<DataSource>())).isInstanceOf(BucketRepository::class.java)
    }

    @Test
    fun `test name placeholder`() {

        // GIVEN
        val bucketId = UUID.randomUUID()
        val position = 1.5
        val name = "TODO"
        val bucket = Bucket(bucketId = bucketId, position = position, name = name)

        // WHEN
        val repository = BucketRepositoryImpl(DataSourceHelper())
        repository.create(bucket)

        // THEN
        val actualBucket = repository.findById(bucketId).get()
        assertThat(actualBucket.bucketId).isEqualTo(bucketId)
        assertThat(actualBucket.position).isEqualTo(position)
        assertThat(actualBucket.name).isEqualTo(name)
    }
}