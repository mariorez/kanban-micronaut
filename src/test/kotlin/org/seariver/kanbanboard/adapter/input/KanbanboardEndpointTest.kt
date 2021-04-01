package org.seariver.kanbanboard.adapter.input

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.seariver.kanbanboard.application.output.BucketRepository
import org.seariver.kanbanboard.grpc.CreateBucketRequest
import org.seariver.kanbanboard.grpc.KanbanboardServiceGrpcKt.KanbanboardServiceCoroutineStub
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
class KanbanboardEndpointTest {

    @Inject
    lateinit var grpcClient: KanbanboardServiceCoroutineStub

    @Inject
    lateinit var bucketRepository: BucketRepository

    @Test
    fun `GIVEN a valid request MUST create bucket successful`() {

        runBlocking {
            // GIVEN
            val bucketId = UUID.randomUUID()
            val position = 1.2
            val name = "TODO"

            val createBucketRequest = CreateBucketRequest.newBuilder()
                .setBucketId(bucketId.toString())
                .setPosition(position)
                .setName(name)
                .build()

            // WHEN
            grpcClient.createBucket(createBucketRequest)

            // THEN
            val actual = bucketRepository.findById(bucketId).get()
            assertThat(actual.position).isEqualTo(position)
            assertThat(actual.name).isEqualTo(name)
        }
    }
}

@Factory
class Clients {
    @Singleton
    fun grpcClient(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): KanbanboardServiceCoroutineStub {
        return KanbanboardServiceCoroutineStub(channel)
    }
}
