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
    fun `GIVEN a valid request WHEN gRPC call create bucket MUST create with success`() {

        runBlocking {
            // GIVEN
            val bucketId = UUID.randomUUID()
            val position = 100.2
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
    fun `WHEN gRPC call find all buckets MUST return the result set in correct order`() {

        runBlocking {
            // WHEN
            val result = grpcClient.findAllBuckets(Empty.newBuilder().build())

            // THEN
            assertThat(result.bucketsList)
                .hasSize(10)
                .extracting("position", "name")
                .startsWith(
                    Assertions.tuple(10.15, "FIRST-BUCKET"),
                    Assertions.tuple(20.987, "SECOND-BUCKET"),
                    Assertions.tuple(30.1, "BUCKET-1")
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
