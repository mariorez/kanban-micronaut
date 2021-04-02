package org.seariver.kanbanboard.adapter.input

import com.google.protobuf.Empty
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.seariver.kanbanboard.application.output.BucketRepository
import org.seariver.kanbanboard.grpc.BucketGrpc
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

            val request = BucketGrpc.newBuilder()
                .setBucketId(bucketId.toString())
                .setPosition(position)
                .setName(name)
                .build()

            // WHEN
            grpcClient.createBucket(request)

            // THEN
            val actual = bucketRepository.findById(bucketId).get()
            assertThat(actual.position).isEqualTo(position)
            assertThat(actual.name).isEqualTo(name)
        }
    }

    @Test
    fun `WHEN find all buckets MUST return the result set`() {

        runBlocking {
            // WHEN
            val result = grpcClient.findAllBuckets(Empty.newBuilder().build())

            // THEN
            assertThat(result.bucketsList)
                .extracting("position", "name")
                .contains(
                    Assertions.tuple(200.987, "SECOND-BUCKET"),
                    Assertions.tuple(100.15, "FIRST-BUCKET")
                )
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
