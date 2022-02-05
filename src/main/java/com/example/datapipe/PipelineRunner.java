package com.example.datapipe;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.example.datapipe.model.Category;
import com.example.datapipe.model.Document;
import com.example.datapipe.service.ICategoryRepository;
import com.example.datapipe.service.IContentService;
import com.example.datapipe.service.IRecipeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * The Data Pipeline task that is responsible for loading the data from the
 * server and storing it in the database
 *
 * @author aanal
 *
 */
@Slf4j
@Service
public class PipelineRunner implements CommandLineRunner {

    private final IContentService contentService;
    private final ICategoryRepository categoryRepository;
    private final IRecipeRepository recipeRepository;

    public PipelineRunner(IContentService contentService,
            ICategoryRepository categoryRepository,
            IRecipeRepository recipeRepository) {
        this.contentService = contentService;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Begin pipeline setup");

        preloadCategories();
        loadDocumentsAndMergeCategories();


        log.info("Done pipeline");
    }

    private void loadDocumentsAndMergeCategories() {
        log.info("Loading Documents from source");
        List<Document> documents = contentService.listDocuments();
        log.info("Loaded Documents: count: {}", documents.size());
        for (Document document : documents) {
            if (document.getCategoryIds() != null && document.getCategoryIds().length != 0) {
                for (String id : document.getCategoryIds()) {
                    Optional<Category> category = categoryRepository.findById(id);
                    if (category.isPresent()) {
                        document.addCategory(category.get());
                    }
                }
                recipeRepository.save(document);
            } else {
                log.warn("Not saving document since a category is not available. id: {}", document.getId());
            }
        }

        log.info("Done loading documents from source");

    }

    private void preloadCategories() {
        log.info("Preloading categories into database");
        List<Category> categories = contentService.listCategories();
        log.info("Loaded categories: count: {}", categories.size());
        categoryRepository.saveAll(categories);
        log.info("Saved categories successfully");

    }

}
