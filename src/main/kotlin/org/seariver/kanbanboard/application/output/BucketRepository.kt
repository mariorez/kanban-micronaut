package org.seariver.kanbanboard.application.output

import org.seariver.kanbanboard.application.domain.Bucket
import java.util.*

interface BucketRepository {

    fun create(bucket: Bucket)

    fun findById(bucketId: UUID): Optional<Bucket>

    fun findAll(pageSize: Int = 10): Set<Bucket>

}
