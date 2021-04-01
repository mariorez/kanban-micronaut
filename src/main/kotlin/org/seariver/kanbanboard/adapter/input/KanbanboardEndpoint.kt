package org.seariver.kanbanboard.adapter.input

import com.google.protobuf.Empty
import org.seariver.kanbanboard.application.usecase.write.CreateBucketCommand
import org.seariver.kanbanboard.application.usecase.write.CreateBucketHandler
import org.seariver.kanbanboard.grpc.CreateBucketRequest
import org.seariver.kanbanboard.grpc.KanbanboardServiceGrpcKt.KanbanboardServiceCoroutineImplBase
import java.util.*
import javax.inject.Singleton

@Singleton
class KanbanboardEndpoint(private val handler: CreateBucketHandler) : KanbanboardServiceCoroutineImplBase() {

    override suspend fun createBucket(request: CreateBucketRequest): Empty {

        val command = CreateBucketCommand(UUID.fromString(request.bucketId), request.position, request.name)
        handler.handle(command)

        return Empty.newBuilder().build()
    }
}