package org.seariver.kanbanboard.application.usecase.read

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.seariver.kanbanboard.application.domain.Bucket
import org.seariver.kanbanboard.application.output.BucketRepository
import java.util.*

class FindAllBucketsHandlerTest {

    @Test
    fun `WHEN find all buckets MUST return the result set`() {

        // GIVEN
        val query = FindAllBucketsQuery()
        val repository = mock<BucketRepository> {
            on { findAll() } doReturn setOf(
                Bucket(UUID.randomUUID(), 1.2, "TODO"),
                Bucket(UUID.randomUUID(), 1.3, "DOING"),
                Bucket(UUID.randomUUID(), 1.4, "DONE")
            )
        }

        // WHEN
        val handler = FindAllBucketsHandler(repository)
        val result: Set<Bucket> = handler.handle(query)

        // THEN
        verify(repository).findAll()
        assertThat(result)
            .hasSize(3)
            .extracting("position", "name")
            .contains(
                tuple(1.2, "TODO"),
                tuple(1.3, "DOING"),
                tuple(1.4, "DONE")
            )
    }
}