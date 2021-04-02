package org.seariver.kanbanboard.adapter.input

import com.google.protobuf.Empty
import org.seariver.kanbanboard.application.usecase.read.FindAllBucketsHandler
import org.seariver.kanbanboard.application.usecase.read.FindAllBucketsQuery
import org.seariver.kanbanboard.application.usecase.write.CreateBucketCommand
import org.seariver.kanbanboard.application.usecase.write.CreateBucketHandler
import org.seariver.kanbanboard.grpc.BucketGrpc
import org.seariver.kanbanboard.grpc.BucketResultSet
import org.seariver.kanbanboard.grpc.KanbanboardServiceGrpcKt.KanbanboardServiceCoroutineImplBase
import java.util.*
import javax.inject.Singleton

@Singleton
class KanbanboardEndpoint(
    private val createBucketHandler: CreateBucketHandler,
    private val findAllBucketsHandler: FindAllBucketsHandler
) : KanbanboardServiceCoroutineImplBase() {

    override suspend fun createBucket(request: BucketGrpc): Empty {

        val command = CreateBucketCommand(UUID.fromString(request.bucketId), request.position, request.name)
        createBucketHandler.handle(command)

        return Empty.newBuilder().build()
    }

    override suspend fun findAllBuckets(request: Empty): BucketResultSet {

        val builder = BucketResultSet.newBuilder()

        findAllBucketsHandler.handle(FindAllBucketsQuery()).forEach {
            builder.addBuckets(
                BucketGrpc.newBuilder()
                    .setBucketId(it.bucketId.toString())
                    .setPosition(it.position)
                    .setName(it.name)
            )
        }

        return builder.build()
    }
}