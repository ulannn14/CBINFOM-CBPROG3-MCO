package View;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AnalystModifyTagsAndCommentsView extends ViewAbstract {

    public AnalystModifyTagsAndCommentsView(int instanceID, String[] summaries, String[] tags, int counterCS, int counterTag) {
        //TODO Auto-generated constructor stub
    }

    public void setDeleteButtonListener(ActionListener listener) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDeleteButtonListener'");
    }

    public void setErrorMessages(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean validateDelete() {
        return false;
    }

    public ArrayList<Integer> getTagIDsToDelete(){
        throw new UnsupportedOperationException("Unimplemented method 'setDeleteButtonListener'");
    }
    
    public ArrayList<Integer> getSummariesIDsToDelete(){
        throw new UnsupportedOperationException("Unimplemented method 'setDeleteButtonListener'");
    }

    public boolean validateAddTag() {
        throw new UnsupportedOperationException("Unimplemented method 'setDeleteButtonListener'");
    }

}
