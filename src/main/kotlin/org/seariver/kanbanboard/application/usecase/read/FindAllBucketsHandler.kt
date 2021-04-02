package org.seariver.kanbanboard.application.usecase.read

import org.seariver.kanbanboard.application.domain.Bucket
import org.seariver.kanbanboard.application.output.BucketRepository
import javax.inject.Singleton

@Singleton
class FindAllBucketsHandler(
    private val repository: BucketRepository
) {
    fun handle(query: FindAllBucketsQuery): Set<Bucket> {
        return repository.findAll()
    }
}
