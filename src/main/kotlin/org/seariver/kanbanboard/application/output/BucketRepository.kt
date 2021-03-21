package org.seariver.kanbanboard.application.output

import org.seariver.kanbanboard.application.domain.Bucket

interface BucketRepository {

    fun create(bucket: Bucket)

}
