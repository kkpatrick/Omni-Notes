/*
 * Copyright (C) 2015 Federico Iosue (federico.iosue@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.feio.android.omninotes.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

public class NotificationsHelper {

	private Context mContext;
	private Builder mBuilder;
	private NotificationManager mNotificationManager;


	public NotificationsHelper(Context mContext) {
		this.mContext = mContext;
	}


	/**
	 * Creation of notification on operations completed
	 * 
	 * @param intent2
	 * @param mContext
	 * @param message
	 */
	public NotificationsHelper createNotification(int icon, String title, PendingIntent notifyIntent) {
		mBuilder = new NotificationCompat.Builder(mContext).setSmallIcon(icon).setContentTitle(title)
				.setAutoCancel(true);
		// Puts the PendingIntent into the notification builder
		mBuilder.setContentIntent(notifyIntent);
		return this;
	}


	public NotificationsHelper setRingtone(String ringtone) {
		// Ringtone options
		if (ringtone != null) {
			mBuilder.setSound(Uri.parse(ringtone));
		}
		return this;
	}


	public NotificationsHelper setVibration() {
		return setVibration(null);
	}


	public NotificationsHelper setVibration(long[] pattern) {
		// Vibration options
		if (pattern == null || pattern.length == 0) {
			pattern = new long[] { 500, 500 };
		}
		mBuilder.setVibrate(pattern);
		return this;
	}


	public NotificationsHelper setIcon(int icon) {
		mBuilder.setSmallIcon(icon);
		return this;
	}


	public NotificationsHelper setMessage(String message) {
		mBuilder.setContentText(message);
		return this;
	}


	public NotificationsHelper setIndeterminate() {
		mBuilder.setProgress(0, 0, true);
		return this;
	}


	public NotificationsHelper setOngoing() {
		mBuilder.setOngoing(true);
		return this;
	}


	public NotificationsHelper show() {
		show(null);
		return this;
	}


	public NotificationsHelper show(Integer id) {
		if (mNotificationManager == null) {
			mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		
		Notification mNotification = mBuilder.build();
		
		if (mNotification.contentIntent == null) {
			// Creates a dummy PendingIntent
			mBuilder.setContentIntent(PendingIntent.getActivity(mContext, 0, new Intent(),
					PendingIntent.FLAG_UPDATE_CURRENT));
		}
		
		// Builds an anonymous Notification object from the builder, and
		// passes it to the NotificationManager
		mNotificationManager.notify(id == null ? 0 : id, mBuilder.build());
		return this;
	}
}
