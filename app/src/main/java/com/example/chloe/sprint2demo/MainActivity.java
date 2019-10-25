package com.example.chloe.sprint2demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArFragment arFragment;
    private ModelRenderable hippoRenderable;

    ImageView hippo;

    View arrayView[];
    ViewRenderable name_animal;

    int selected = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);

        hippo = (ImageView)findViewById(R.id.hippo);

        setArrayView();
        setClickListener();

        setupModel();

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                //When user tap on plane, add the model
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    createModel(anchorNode, selected);
            }
        });


    }

    private void setupModel() {
        ModelRenderable.builder().setSource(this, R.raw.hippopotamus)
                .build().thenAccept(renderable -> hippoRenderable = renderable)
                .exceptionally(
                        throwable ->{
                            Toast toast = Toast.makeText(this, "Unnable to load hippo model", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        return null;
                });



    }

    private void createModel(AnchorNode anchorNode, int selected) {
        if(selected == 1){
            TransformableNode hippo = new TransformableNode(arFragment.getTransformationSystem());
            hippo.setParent(anchorNode);
            hippo.setRenderable(hippoRenderable);
            hippo.select();
        }
    }

    private void setArrayView(){
        arrayView = new View[]{hippo};
    }

    private void setClickListener(){
        for (int i = 0; i < arrayView.length; i++) {
            arrayView[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
