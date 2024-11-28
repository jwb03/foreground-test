package com.donghaeng.withme.roomdatabase.guide;

import android.content.Context;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuideBookRepository {
    private GuideBookDao guideBookDao;
    private ExecutorService executorService;

    public GuideBookRepository(Context context) {
        GuideBookDatabase db = GuideBookDatabase.getInstance(context);
        guideBookDao = db.guideBookDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(GuideBook guideBook) {
        executorService.execute(() -> guideBookDao.insert(guideBook));
    }

    public void update(GuideBook guideBook) {
        executorService.execute(() -> guideBookDao.update(guideBook));
    }

    public void delete(GuideBook guideBook) {
        executorService.execute(() -> guideBookDao.delete(guideBook));
    }

    public List<GuideBook> getAllGuides() {
        return guideBookDao.getAllGuides();
    }

    public GuideBook getGuideById(int guideIndex) {
        return guideBookDao.getGuideById(guideIndex);
    }

    public List<GuideBook> getAppGuides() {
        return guideBookDao.getGuidesByType(GuideBookType.APP_GUIDE_BOOK);
    }

    public List<GuideBook> getSmartphoneGuides() {
        return guideBookDao.getGuidesByType(GuideBookType.SMARTPHONE_GUIDE_BOOK);
    }

    public List<GuideBook> getControllerInstructions() {
        return guideBookDao.getGuidesByType(GuideBookType.CONTROLLER_INSTRUCTION);
    }
}