
package com.wherego.delivery.driver.model.documents;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DocumentList {

    @SerializedName("document")
    private List<Document> mDocument;
    @SerializedName("status")
    private int mStatus;

    public List<Document> getDocument() {
        return mDocument;
    }

    public void setDocument(List<Document> document) {
        mDocument = document;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    private String term;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
