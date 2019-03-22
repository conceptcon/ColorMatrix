package com.imageeditor

import android.content.Context
import android.view.View
import com.miracle.view.imageeditor.layer.LayerComposite
import com.miracle.view.imageeditor.layer.RootEditorDelegate
import com.miracle.view.imageeditor.view.CropHelper
import com.miracle.view.imageeditor.view.EditorMode
import com.miracle.view.imageeditor.view.FuncAndActionBarAnimHelper

/**
 * # UI element provider
 *
 * Created by lxw
 */
interface LayerViewProvider {

    fun findLayerByEditorMode(editorMode: EditorMode): View?

    fun getActivityContext(): Context

    fun getFuncAndActionBarAnimHelper(): FuncAndActionBarAnimHelper

    fun getCropHelper(): CropHelper

    fun getRootEditorDelegate(): RootEditorDelegate

    fun getLayerCompositeView(): LayerComposite

    fun getSetupEditorId(): String

    fun getResultEditorId(): String

    fun getEditorSizeInfo(): Pair<Int, Int>

    fun getScreenSizeInfo(): Pair<Int, Int>

}