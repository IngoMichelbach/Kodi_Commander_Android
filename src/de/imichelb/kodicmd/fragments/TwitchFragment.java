package de.imichelb.kodicmd.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import de.imichelb.kodicmd.R;
import de.imichelb.kodicmd.kodi.Command;
import de.imichelb.kodicmd.model.TwitchItem;
import de.imichelb.kodicmd.tasks.KodiCommandTask;
import de.imichelb.kodicmd.tasks.TwitchDataTask;
import de.imichelb.kodicmd.twitch.TwitchInterface;

public class TwitchFragment extends Fragment implements OnItemClickListener, TwitchInterface{
	
	private View twitchView;
	private ProgressBar progress;
	private ListView list;
	private Context context;
	private ArrayList<TwitchItem> twitchItems;
	
	private final String TWITCH_URI = "plugin://plugin.video.twitch/playLive/";
	
	public TwitchFragment(Context context){
		
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        twitchView = inflater.inflate(R.layout.twitch, container, false);
        progress = (ProgressBar) twitchView.findViewById(R.id.twitch_progress_bar);
        list = (ListView) twitchView.findViewById(R.id.twitch_stream_list);
        list.setOnItemClickListener(this);
        
        loadTwitchData();
        
        return twitchView;
    }
	
	private void loadTwitchData(){
		
		new TwitchDataTask(context,progress,list, this).execute();
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int pos, long arg3) {
		
		String uri = TWITCH_URI + twitchItems.get(pos).getStreamer() + "/";
		
		//start stream on Kodi
		new KodiCommandTask(context,Command.OPEN_STREAM, uri).execute();
		
		//Info Toast
		Toast.makeText(context, R.string.play_stream, Toast.LENGTH_LONG).show();
	}

	@Override
	public void setTwitchList(ArrayList<TwitchItem> list) {
		
		twitchItems = list;
	}

}
