package org.seariver.kanbanboard.application.usecase.write

import org.seariver.kanbanboard.application.domain.Bucket
import org.seariver.kanbanboard.application.output.BucketRepository

class CreateBucketHandler(
    private val repository: BucketRepository
) {
    fun handle(command: CreateBucketCommand) {

        repository.create(
            Bucket(
                command.bucketId,
                command.position,
                command.name
            )
        )
    }
}
