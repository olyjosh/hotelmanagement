/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hotels.util;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.animation.StrokeTransitionBuilder;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Olyjosh
 */
public class Anim {

    private void strokeTransitionEffect(AnchorPane root) {
        Rectangle rect = new Rectangle(0, 0, root.getPrefWidth() + 2, root.getPrefHeight() + 2);
        rect.setArcHeight(20);
        rect.setArcWidth(20);
        rect.setFill(null);
        rect.setStroke(Color.DODGERBLUE);
        rect.setStrokeWidth(10);
        root.getChildren().add(rect);

        StrokeTransition strokeTransition = StrokeTransitionBuilder.create()
                .duration(Duration.seconds(3))
                .shape(rect)
                .fromValue(Color.RED)
                .toValue(Color.DODGERBLUE)
                .cycleCount(Timeline.INDEFINITE)
                .autoReverse(true)
                .build();
        strokeTransition.play();
    }
    
    private void fadeEffect(HBox h) {
        FadeTransition fadeTransition = FadeTransitionBuilder.create()
                .duration(Duration.seconds(5))
                .node(h)
                .fromValue(1)
                .toValue(0.5)
                .cycleCount(Timeline.INDEFINITE)
                .autoReverse(true)
                .build();
        fadeTransition.play();
    }

    
    public static void seqencetialEffect(Node x) {
        TranslateTransition ta = new TranslateTransition(Duration.seconds(0.5));
        ta.setFromY(-20);
        ta.setToY(0);
        SequentialTransition animation = new SequentialTransition(ta);
        animation.setNode(x);
        animation.play();
    }
    
}
