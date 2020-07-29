import 'package:flutter/material.dart';

///内容发送框
class SendTextBigFieldWidget extends StatelessWidget {
  final ValueChanged<String> onSubmitted;
  final VoidCallback onTab;
  final String hintText;
  final EdgeInsetsGeometry margin;
  final bool autoFocus;
  final double height;

  SendTextBigFieldWidget(
      {Key key,
      this.hintText,
      this.onSubmitted,
      this.onTab,
      this.margin,
      this.autoFocus,
      this.height})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: margin == null ? EdgeInsets.all(0.0) : margin,
      width: MediaQuery.of(context).size.width,
      height: height,
      decoration: BoxDecoration(
          color: Color.fromARGB(255, 237, 236, 237),
          borderRadius: BorderRadius.circular(24.0)),
      child: Column(
        children: <Widget>[
          TextField(
            autofocus: autoFocus,
            onSubmitted: onSubmitted,
            onTap: onTab,
            cursorColor: Colors.black,
            decoration: InputDecoration(
                contentPadding:
                    EdgeInsets.only(left: 10, top: 5, bottom: 5, right: 10),
                hintText: hintText,
                hintStyle: TextStyle(fontSize: 12),
                isDense: true,
                border: const OutlineInputBorder(
                  gapPadding: 0,
                  borderSide: BorderSide(
                    width: 0,
                    style: BorderStyle.none,
                  ),
                )),
            style: TextStyle(fontSize: 16),
            minLines: 5,
            maxLines: 8,
          ),
          Spacer(),
          Container(
            margin: EdgeInsets.all(10),
            child: Row(
              children: <Widget>[
                Spacer(),
                IconButton(
                    icon: Icon(Icons.send, color: Colors.black12),
                    onPressed: () => {})
              ],
            ),
          ),
        ],
      ),
    );
  }

  getContainer(BuildContext context, ValueChanged<String> onSubmitted) {
    return Container(
      height: height,
      width: MediaQuery.of(context).size.width,
      decoration: BoxDecoration(
          color: Color.fromARGB(255, 237, 236, 237),
          borderRadius: BorderRadius.circular(24.0)),
      child: TextField(
        autofocus: autoFocus,
        onSubmitted: onSubmitted,
        cursorColor: Colors.black,
        decoration: InputDecoration(
            contentPadding:
                EdgeInsets.only(left: 10, top: 5, bottom: 5, right: 10),
            hintText: hintText,
            hintStyle: TextStyle(fontSize: 12),
            isDense: true,
            border: const OutlineInputBorder(
              gapPadding: 0,
              borderSide: BorderSide(
                width: 0,
                style: BorderStyle.none,
              ),
            )),
        style: TextStyle(fontSize: 16),
        minLines: 5,
        maxLines: 8,
      ),
    );
  }
}
