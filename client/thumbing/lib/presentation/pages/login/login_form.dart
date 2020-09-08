import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/logic/bloc/user/login_bloc.dart';
import 'package:thumbing/logic/state/user/login_state.dart';
import 'package:thumbing/logic/event/user/login_event.dart';

class LoginForm extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocListener<LoginBloc, LoginState>(
      listener: (context, state) {
        if (state is LoginFailure) {
          Scaffold.of(context)
            ..hideCurrentSnackBar()
            ..showSnackBar(
              SnackBar(content: Text(state.message)),
            );
        }
      },
      child: Align(
        alignment: const Alignment(0, -1 / 3),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            _UsernameInput(),
            const Padding(padding: EdgeInsets.all(12)),
            _PasswordInput(),
            const Padding(padding: EdgeInsets.all(12)),
            _LoginButton(),
          ],
        ),
      ),
    );
  }
}

class _UsernameInput extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<LoginBloc, LoginState>(
      builder: (context, state) {
        return TextField(
          key: const Key('loginForm_usernameInput_textField'),
          onChanged: (username) => {
            context
                .bloc<LoginBloc>()
                .add(LoginUsernameChanged(userName: username))
          },
          decoration: InputDecoration(
            labelText: '用户名',
            errorText: state is LoginInitial && state.isUserNameInValid()
                ? '用户名不符合规则'
                : null,
          ),
        );
      },
    );
  }
}

class _PasswordInput extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<LoginBloc, LoginState>(
      builder: (context, state) {
        return TextField(
          key: const Key('loginForm_passwordInput_textField'),
          onChanged: (password) => context
              .bloc<LoginBloc>()
              .add(LoginPasswordChanged(password: password)),
          obscureText: true,
          decoration: InputDecoration(
            labelText: '密码',
            errorText: state is LoginInitial && state.isPasswordInValid()
                ? '密码不符合规则'
                : null,
          ),
        );
      },
    );
  }
}

class _LoginButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<LoginBloc, LoginState>(
      //buildWhen: (previous, current) => previous.status != current.status,
      builder: (context, state) {
        return state is SubmissionInProgress
            ? const CircularProgressIndicator()
            : RaisedButton(
                color: Colors.indigoAccent,
                textColor: Colors.white,
                key: const Key('loginForm_continue_raisedButton'),
                child: const Text('登录'),
                onPressed: state is LoginInitial &&
                        !state.isUserNameInValid() &&
                        !state.isPasswordInValid()
                    ? () {
                        context.bloc<LoginBloc>().add(Login(
                            userName: state.userName,
                            password: state.password));
                      }
                    : null,
              );
      },
    );
  }
}
