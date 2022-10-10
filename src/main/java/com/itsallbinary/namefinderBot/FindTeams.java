/*
package com.itsallbinary.namefinderBot;

import com.itsallbinary.OpenNLPChatBot;
import opennlp.tools.cmdline.SystemInputStreamFactory;
import opennlp.tools.cmdline.doccat.DoccatEvaluatorTool;
import opennlp.tools.doccat.*;
import opennlp.tools.ml.maxent.GISTrainer;
import opennlp.tools.namefind.*;
import opennlp.tools.util.*;
import opennlp.tools.util.eval.FMeasure;
import opennlp.tools.util.model.ModelUtil;

import javax.swing.event.DocumentListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static opennlp.tools.ml.TrainerFactory.TrainerType.SEQUENCE_TRAINER;

public class FindTeams {

    private static Map<String, String> questionAnswer = new HashMap<>();
    static OpenNLPChatBot openNLPChatBot = new OpenNLPChatBot();

    static {
        questionAnswer.put("greeting", "Hello, how can I help you?");
        questionAnswer.put("team-names", "Stratus, NynemKonto, Emerging Tech & Innovation");
//        questionAnswer.put("stratus-enquiry", "Mamatha, Srinivas, Henrik, Mads, Shilpa");
        questionAnswer.put("nynemkonto-enquiry", "Mamatha, Srinivas, Henrik, Mads, Shilpa, Shaista, Pranati, Nithya, Karthik, Shrinivasa");
        questionAnswer.put("emergingTech-enquiry", "Mamatha, Shaista, Shrusti, Shilpa, Karthik, Shrinivasa");
        questionAnswer.put("team-experience", "5");
        questionAnswer.put("conversation-continue", "What else can I help you with?");
        questionAnswer.put("conversation-complete", "Nice chatting with you. Bbye.");

    }

    public static void main(String[] args) throws IOException {

        InputStreamFactory isf = () -> new FileInputStream("team-namefinder.txt");
        Charset charset = Charset.forName("UTF-8");
        ObjectStream<String> lineStream = new PlainTextByLineStream(isf, charset);
        ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);

        TokenNameFinderModel model;
        TokenNameFinderFactory nameFinderFactory = new TokenNameFinderFactory();

        try {
            model = NameFinderME.train("en", null, sampleStream, TrainingParameters.defaultParams(),
                    nameFinderFactory);
        } finally {
            sampleStream.close();
        }

        BufferedOutputStream modelOut = null;

        try {
            modelOut = new BufferedOutputStream(new FileOutputStream("ner-custom-model.bin"));
            model.serialize(modelOut);
        } finally {
            if (modelOut != null)
                modelOut.close();
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("##### You:");
            String userInput = scanner.nextLine();

            String[] sentences = openNLPChatBot.breakSentences(userInput);

            String answer = "";
            boolean conversationComplete = false;
            for (String sentence : sentences) {

                String[] tokens = openNLPChatBot.tokenizeSentence(sentence);

                String[] posTags = openNLPChatBot.detectPOSTags(tokens);

                String[] lemmas = openNLPChatBot.lemmatizeTokens(tokens, posTags);
                DoccatModel categorizeModel = openNLPChatBot.trainCategorizerModel();

                String category = openNLPChatBot.detectCategory(categorizeModel, lemmas);

                answer = answer + " " + questionAnswer.get(category);

                if ("conversation-complete".equals(category)) {
                    conversationComplete = true;
                }
            }
//            TokenNameFinderEvaluator evaluator = new TokenNameFinderEvaluator(new NameFinderME(model));
//            evaluator.evaluate(sampleStream);

//            FMeasure result = evaluator.getFMeasure();

//            System.out.println(result.toString());
            System.out.println("##### Chat Bot: " + answer);
            if (conversationComplete) {
                break;
            }
        }

    }

//        TokenNameFinderModel model = trainNameFinderModel();
//        System.out.println(Arrays.toString(model.getNameFinderSequenceModel().getOutcomes()));
//        nameFinder();
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//
//            System.out.println("##### You:");
//            String userInput = scanner.nextLine();
//
//            String[] sentences = openNLPChatBot.breakSentences(userInput);
//
//            String answer = "";
//            boolean conversationComplete = false;

//            for (String sentence : sentences) {
//
//                String[] tokens = openNLPChatBot.tokenizeSentence(sentence);
//
//                String[] posTags = openNLPChatBot.detectPOSTags(tokens);
//
//                String[] lemmas = openNLPChatBot.lemmatizeTokens(tokens, posTags);
//
//                String category = openNLPChatBot.detectCategory(model, lemmas);
//
//                answer = answer + " " + questionAnswer.get(category);
//
//                if ("conversation-complete".equals(category)) {
//                    conversationComplete = true;
//                }
//            }
////            TokenNameFinderEvaluator evaluator = new TokenNameFinderEvaluator(new NameFinderME(model));
////            evaluator.evaluate(sampleStream);
//
////            FMeasure result = evaluator.getFMeasure();
//
////            System.out.println(result.toString());
//            System.out.println("##### Chat Bot: " + answer);
//            if (conversationComplete) {
//                break;
//            }

        }

//    public static void nameFinder() throws IOException {
//        TokenNameFinder nameFinder = new NameFinderME(trainNameFinderModel());
//        InputStreamFactory sampleDataIn = (InputStreamFactory) new FileInputStream("en-ner-person.bin");
//        ObjectStream sampleStream = new PlainTextByLineStream(sampleDataIn, StandardCharsets.UTF_8);
//        TokenNameFinderEvaluator evaluator = new TokenNameFinderEvaluator(nameFinder);
//        evaluator.evaluate(sampleStream);
//
//        FMeasure result = evaluator.getFMeasure();
//
//        System.out.println(result.toString());
//    }
//
//    public static TokenNameFinderModel trainNameFinderModel() throws IOException {
//        InputStreamFactory inputStreamFactory = () -> new FileInputStream("team-namefinder.txt");
//        ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
//        ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);
//
//        TokenNameFinderFactory factory = new TokenNameFinderFactory();
//
//        TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
//        params.put(TrainingParameters.CUTOFF_PARAM, 1);
//        params.put(TrainingParameters.TRAINER_TYPE_PARAM, String.valueOf(SEQUENCE_TRAINER));
//
//        TokenNameFinderModel model = NameFinderME.train("en", null,sampleStream, params, factory);
//        return model;
//    }
*/
