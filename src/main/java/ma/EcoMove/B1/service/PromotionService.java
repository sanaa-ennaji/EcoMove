package main.java.ma.EcoMove.B1.service;

import main.java.ma.EcoMove.B1.dao.Interface.IPromotion;
import main.java.ma.EcoMove.B1.entity.Promotion;
import main.java.ma.EcoMove.B1.service.IService.IPromotionService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PromotionService implements IPromotionService {
    private final IPromotion promotionDAO;

    public PromotionService(IPromotion promotionDAO) {
        this.promotionDAO = promotionDAO;
    }

    @Override
    public void createPromotion(Promotion promotion) throws SQLException {
        // Add any additional business logic here if needed
        promotionDAO.createPromotion(promotion);
    }

    @Override
    public Promotion getPromotionById(UUID id) throws SQLException {
        return promotionDAO.getPromotionById(id);
    }

    @Override
    public List<Promotion> getAllPromotions() throws SQLException {
        return promotionDAO.getAllPromotions();
    }

    @Override
    public void updatePromotion(Promotion promotion) throws SQLException {

        promotionDAO.updatePromotion(promotion);
    }

    @Override
    public void deletePromotion(UUID id) throws SQLException {
        promotionDAO.deletePromotion(id);
    }
}
