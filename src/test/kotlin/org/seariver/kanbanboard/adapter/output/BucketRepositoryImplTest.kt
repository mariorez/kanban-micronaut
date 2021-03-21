package org.seariver.kanbanboard.adapter.output

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.seariver.kanbanboard.application.output.BucketRepository

class BucketRepositoryImplTest {

    @Test
    fun `MUST be an instance of BucketRepository`() {
        assertThat(BucketRepositoryImpl()).isInstanceOf(BucketRepository::class.java)
    }
}