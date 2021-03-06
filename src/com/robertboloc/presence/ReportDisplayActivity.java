package com.robertboloc.presence;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.robertboloc.presence.lib.PresenceApiClient;
import com.robertboloc.presence.pojo.Report;

public class ReportDisplayActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_display);

		this.refresh();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.refresh_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.refresh:
			this.refresh();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void refresh() {
		
		// obtain the user params
		Intent i = this.getIntent();
		String start = i.getStringExtra("start");
		String end = i.getStringExtra("end");

		// instance of the api client
		PresenceApiClient client = new PresenceApiClient(
				getApplicationContext());

		// append the user params
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("start", start));
		params.add(new BasicNameValuePair("end", end));

		// get the user report
		Report report = client.getUserReport(params);

		// display the report
		final TextView periodView = (TextView) findViewById(R.id.reportPeriod);
		periodView.setText(report.getStart().replace('-', '/') + " - "
				+ report.getEnd().replace('-', '/'));

		final TextView timeView = (TextView) findViewById(R.id.reportTime);
		timeView.setText(report.getTime());

		final TextView checkinsView = (TextView) findViewById(R.id.reportCheckins);
		checkinsView.setText(report.getCheckins());

		final TextView incidencesView = (TextView) findViewById(R.id.reportIncidences);
		incidencesView.setText(report.getIncidences());
	}
}
