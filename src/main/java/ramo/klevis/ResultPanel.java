package ramo.klevis;

import javax.swing.*;

public class ResultPanel extends JPanel implements Watcher{

    //do we really need this to be an observer? we should find out if he wants the functionality changed
    //if you need to hit a button for it to do the training, theres no reason for there to be an observer
    //because it doesn't have to update on the fly
    public ResultPanel() {
        super();
    }

    @Override
    public void update() {

    }
}
