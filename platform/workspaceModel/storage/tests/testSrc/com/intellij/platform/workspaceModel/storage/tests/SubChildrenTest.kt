// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.platform.workspaceModel.storage.tests

import com.intellij.platform.workspaceModel.storage.testEntities.entities.ChildSubEntity
import com.intellij.platform.workspaceModel.storage.testEntities.entities.ChildSubSubEntity
import com.intellij.platform.workspaceModel.storage.testEntities.entities.MySource
import com.intellij.platform.workspaceModel.storage.testEntities.entities.ParentSubEntity
import com.intellij.workspaceModel.storage.MutableEntityStorage
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SubChildrenTest {
  @Test
  fun `parent with child`() {
    val entity = ParentSubEntity("ParentData", MySource) {
      child = ChildSubEntity(MySource) {
        child = ChildSubSubEntity("ChildData", MySource)
      }
    }

    assertEquals("ChildData", entity.child?.child?.childData)
  }

  @Test
  fun `parent with child in builder`() {
    val entity = ParentSubEntity("ParentData", MySource) {
      child = ChildSubEntity(MySource) {
        child = ChildSubSubEntity("ChildData", MySource)
      }
    }

    val builder = MutableEntityStorage.create()
    builder.addEntity(entity)

    val parentEntity = builder.entities(ParentSubEntity::class.java).single()
    assertEquals("ChildData", parentEntity.child?.child?.childData)
  }

  @Test
  fun `parent with child in builder and accessing`() {
    val entity = ParentSubEntity("ParentData", MySource) {
      child = ChildSubEntity(MySource) {
        child = ChildSubSubEntity("ChildData", MySource)
      }
    }


    val builder = MutableEntityStorage.create()
    builder.addEntity(entity)

    assertEquals("ChildData", entity.child?.child?.childData)
  }

  @Test
  fun `get parent from child`() {
    val entity = ParentSubEntity("ParentData", MySource) {
      child = ChildSubEntity(MySource) {
        child = ChildSubSubEntity("ChildData", MySource)
      }
    }

    val builder = MutableEntityStorage.create()
    builder.addEntity(entity)

    val childEntity = builder.entities(ChildSubSubEntity::class.java).single()
    assertEquals("ParentData", childEntity.parentEntity.parentEntity.parentData)
  }
}