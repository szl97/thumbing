import 'package:flutter/material.dart';

///文本发送框
class SendTextFieldWidget extends StatelessWidget {
  final ValueChanged<String> onSubmitted;
  final VoidCallback onTab;
  final String hintText;
  final EdgeInsetsGeometry margin;
  final bool autoFocus;

  SendTextFieldWidget(
      {Key key,
      this.hintText,
      this.onSubmitted,
      this.onTab,
      this.margin,
      this.autoFocus})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: margin == null ? EdgeInsets.all(0.0) : margin,
      width: MediaQuery.of(context).size.width,
      alignment: AlignmentDirectional.center,
      decoration: BoxDecoration(
          color: Color.fromARGB(255, 237, 236, 237),
          borderRadius: BorderRadius.circular(24.0)),
      child: TextField(
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
        minLines: 1,
        maxLines: 5,
      ),
    );
  }

  getContainer(BuildContext context, ValueChanged<String> onSubmitted) {
    return Container(
      width: MediaQuery.of(context).size.width,
      alignment: AlignmentDirectional.center,
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
        minLines: 1,
        maxLines: 5,
      ),
    );
  }
}
