package core.processor.impl;

import bean.result.CnkiResult;
import bean.searchquery.impl.CnkiSearchQuery;
import core.processor.PaperProcessor;

import javax.swing.*;

/**
 * @author yagol
 * @date 2022/10/12
 */
public class CnkiResultProcessor implements PaperProcessor<CnkiSearchQuery, CnkiResult> {
    @Override
    public CnkiResult run(CnkiSearchQuery searchQuery, Integer searchLimit) {

        return null;
    }

    @Override
    public CnkiResult run(CnkiSearchQuery searchQuery, JTextArea jTextArea, Integer searchLimit) {
        return null;
    }

    @Override
    public CnkiResult run(CnkiSearchQuery searchQuery, JTextArea jTextArea, JProgressBar jProgressBar, Integer searchLimit) {
        return null;
    }
}
