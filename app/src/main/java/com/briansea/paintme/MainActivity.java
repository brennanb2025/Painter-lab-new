package com.briansea.paintme;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private LinearLayout toolbox;
    private DrawingPane drawingArea;
    private Button clearButton;
    private Button deleteButton;
    private Button moveButton;
    private boolean moveBtnDown = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab the objects from the UI
        toolbox = findViewById(R.id.toolbox);
        drawingArea = findViewById( R.id.drawingarea );

        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new clearButtonListener());

        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new deleteButtonListener());

        moveButton = findViewById(R.id.moveBtn);
        moveButton.setOnClickListener(new moveButtonListener());


        // Stamps to create buttons for
        // TODO: Add new stamps below
        Stamp[] tools = { new HexStamp(), new RectStamp(), new EllipseStamp(), new TriStamp() };

        View.OnClickListener listener = new ToolSelector();

        // Create the tool buttons and attach listeners
        for( Stamp tool : tools ) {
            Tool t = new Tool(toolbox.getContext(), null);
            t.setMinimumHeight(200);
            t.setStamp(tool);
            t.setOnClickListener( listener );
            toolbox.addView(t);
        }
    }

    public class ToolSelector implements View.OnClickListener {
        public void onClick( View view ) {
            drawingArea.setMoveBtnDown(false);
            Tool selectedTool = (Tool) view;
            drawingArea.setStamp(selectedTool.getStamp().newInstance());
        }
    }

    private class clearButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            drawingArea.setMoveBtnDown(false);
            drawingArea.setStamps(new ArrayList<Stamp>(), new ArrayList<Paint>());
        }
    }

    private class deleteButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            drawingArea.setMoveBtnDown(false);
            ArrayList<Stamp> stampNew = drawingArea.getStamps();
            ArrayList<Paint> paintNew = drawingArea.getPaints();
            ArrayList<Stamp> replaceStamp = new ArrayList<>();
            ArrayList<Paint> replacePaint = new ArrayList<>();
            for(int i = 0; i < stampNew.size()-1; i++) {
                replacePaint.add(paintNew.get(i));
                replaceStamp.add(stampNew.get(i));
            }

            drawingArea.setStamps(replaceStamp, replacePaint);
        }
    }

    private class moveButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            drawingArea.setMoveBtnDown(true);
        }
    }

}