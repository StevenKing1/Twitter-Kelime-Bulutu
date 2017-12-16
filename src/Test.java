
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.kennycason.kumo.*;
import com.kennycason.kumo.bg.*;
import com.kennycason.kumo.font.scale.*;
import com.kennycason.kumo.nlp.*;
import com.kennycason.kumo.palette.ColorPalette;

public class Test {

	public static void main(String[] args) throws IOException {
		TwitterClient client = new TwitterClient("6QDCxa3Sm3PWjURHhpfGdQm6R","uG33entBnvRPo5TJ5rmV52P3qaS34OwDqxuO1qGAQYginY526I","892522267854372865-DA2l0E6w6KyULzBHNVmLj1QxSV0LZe4","tGTR4VYOfhpXC5bEETIOYnqALk95ZUIScCMuAwUkLuwo2");
		
		String keyword = "Twitter";
		ArrayList<String> deneme = client.getTweets(keyword);
		ArrayList<String> allWords = new ArrayList<>();
		String[] words;
		
		for(int i=0;i<deneme.size();i++)
		{
			words = deneme.get(i).split("\\s");
			for (int x=0; x<words.length; x++) {
				//Filtreleme
				if(!words[x].startsWith("@") || !words[x].startsWith("#"))
				{
					allWords.add(words[x]);
				}
			  }
		}
				
		final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(allWords);
		final Dimension dimension = new Dimension(600, 600);
		final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
		wordCloud.setPadding(2);
		wordCloud.setBackground(new CircleBackground(300));
		wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
		wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
		wordCloud.build(wordFrequencies);
		wordCloud.writeToFile("output/datarank_wordcloud_circle_sqrt_font.png");
    }

}
